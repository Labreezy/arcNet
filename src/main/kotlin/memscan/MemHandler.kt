package memscan

import com.sun.jna.Memory
import com.sun.jna.Pointer
import org.jire.kotmem.win32.Kernel32.ReadProcessMemory
import org.jire.kotmem.win32.Win32Process
import org.jire.kotmem.win32.openProcess
import org.jire.kotmem.win32.processIDByName
import utils.truncate
import java.nio.ByteBuffer


class MemHandler : XrdApi {
    override fun getLobbyData(): LobbyData {
        TODO("not implemented")
    }

    var GG_PROC: Win32Process? = null

    override fun isConnected(): Boolean {
        try { GG_PROC = openProcess(processIDByName("GuiltyGearXrd.exe"))
            return true
        } catch (e: IllegalStateException) {
            return false
        }
    }

    @UseExperimental(ExperimentalUnsignedTypes::class)
    private fun getByteBufferFromAddress(offsets: LongArray, numBytes: Int): ByteBuffer? {
        if(!isConnected()){
            return null
        }
        val procBaseAddr: Pointer = GG_PROC!!.modules["GuiltyGearXrd.exe"]!!.pointer
        var bufferMem = Memory(4L)
        var lastPointer: Pointer = procBaseAddr
        for (i in 0..offsets.size - 2) {
            val newPointer = Pointer(Pointer.nativeValue(lastPointer) + offsets[i])
            if (ReadProcessMemory(GG_PROC!!.handle.pointer, newPointer, bufferMem, 4, 0) == 0L) {
                return null
            }
            lastPointer = Pointer(bufferMem.getInt(0L).toUInt().toLong()) //toUInt used due to sign issues explained earlier
        }
        var dataAddr = Pointer(Pointer.nativeValue(lastPointer) + offsets[offsets.size - 1])
        bufferMem = Memory(numBytes.toLong())
        if (ReadProcessMemory(GG_PROC!!.handle.pointer, dataAddr, bufferMem, numBytes, 0) == 0L) {
            return null
//          throw IllegalAccessError("ReadProcMemory returned 0!")

        }
        return bufferMem.getByteBuffer(0L, numBytes.toLong())
    }

    override fun getPlayerData() : List<PlayerData> {
        if(!isConnected()) return ArrayList()

        var offs = longArrayOf(0x1C25AB4L, 0x44CL)
        var pDatas = ArrayList<PlayerData>()
        for (i in 0..7) {
            var bb = getByteBufferFromAddress(offs, 0x48)
            if (bb == null) return ArrayList()
            var dispbytes = ByteArray(0x24)
            var steamid = bb.getLong(0)
            var totalmatch = bb.get(8).toInt()
            var wins = bb.get(0xA).toInt()
            var charid = bb.get(0x36)
            var cabid = bb.get(0x38)
            var playerside = bb.get(0x39)
            var loadpercent = bb.get(0x44).toInt()
            bb.position(0xC)
            bb.get(dispbytes, 0, 0x24)
            var dispname  = truncate(String(dispbytes).trim('\u0000'), 24)
            var pd = PlayerData(steamid, dispname, charid, cabid, playerside, wins, totalmatch , loadpercent)

            offs[1] += 0x48L
            pDatas.add(pd)
        }
        return pDatas
    }


    override fun getMatchData(): MatchData {
        val sortedStructOffs = longArrayOf(0x9CCL, 0x2888L, 0xA0F4L, 0x22960, 0x2AC64)
        var p1offs = longArrayOf(0x1B18C78L,0L)
        var p2offs = longArrayOf(0x1B18C78L,0L)
        p2offs[0] += 4L
        val p1roundoffset = longArrayOf(0x1A3BA38L)
        val p2roundoffset = longArrayOf(0x1A3BA3CL)
        val timeroffs = longArrayOf(0x177A8ACL, 0x450L, 0x4CL, 0x708L)
        try {
            p1offs[1] = sortedStructOffs[0]
            p2offs[1] = sortedStructOffs[0]
            var healths = Pair(getByteBufferFromAddress(p1offs, 4)!!.getInt(), getByteBufferFromAddress(p2offs, 4)!!.getInt())
            p1offs[1] = sortedStructOffs[1]
            p2offs[1] = sortedStructOffs[1]
            var isHits = Pair(getByteBufferFromAddress(p1offs, 4)!!.getInt() == 1, getByteBufferFromAddress(p2offs, 4)!!.getInt() == 1)
            p1offs[1] = sortedStructOffs[2]
            p2offs[1] = sortedStructOffs[2]
            var burstReadies = Pair(getByteBufferFromAddress(p1offs, 4)!!.getInt() == 1, getByteBufferFromAddress(p2offs, 4)!!.getInt() == 1)
            p1offs[1] = sortedStructOffs[3]
            p2offs[1] = sortedStructOffs[3]
            var riscs = Pair(getByteBufferFromAddress(p1offs, 4)!!.getInt(), getByteBufferFromAddress(p2offs, 4)!!.getInt())
            p1offs[1] = sortedStructOffs[4]
            p2offs[1] = sortedStructOffs[4]
            var tensions = Pair(getByteBufferFromAddress(p1offs, 4)!!.getInt(), getByteBufferFromAddress(p2offs, 4)!!.getInt())
            var timer = getByteBufferFromAddress(timeroffs, 4)!!.getInt()
            var rounds = Pair(getByteBufferFromAddress(p1roundoffset, 4)!!.getInt(), getByteBufferFromAddress(p2roundoffset, 4)!!.getInt())
            return MatchData(tensions, healths, burstReadies, riscs, isHits, timer, rounds)
        } catch (e : NullPointerException) {
            return MatchData(Pair(-1,-1), Pair(-1,-1), Pair(false, false), Pair(-1,-1), Pair(false, false), -1, Pair(-1,-1))
        }
    }

}