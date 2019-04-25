import com.sun.jna.Memory
import org.jire.kotmem.win32.*
import java.nio.ByteBuffer
import com.sun.jna.Pointer
import org.jire.kotmem.win32.Kernel32.ReadProcessMemory
class MemHandler : XrdApi {
    var GG_PROC: Win32Process? = null;

    override fun isXrdConnected(): Boolean {
        if (GG_PROC != null) return true
        try {
            GG_PROC = openProcess(processIDByName("GuiltyGearXrd.exe"))
        } catch (e: IllegalStateException) {
            return false
        }
        return true
    }

    fun getByteBufferFromAddress(offsets: LongArray, numBytes: Int): ByteBuffer {
        val procBaseAddr: Pointer = GG_PROC!!.modules["GuiltyGearXrd.exe"]!!.pointer
        var bufferMem = Memory(4L)
        var lastPointer: Pointer = procBaseAddr
        var newPointer = Pointer.NULL
        for (i in 0..offsets.size - 2) {
            newPointer = Pointer(Pointer.nativeValue(lastPointer) + offsets[i])
            if (ReadProcessMemory(GG_PROC!!.handle.pointer, newPointer, bufferMem, 4, 0) == 0L) {
                throw IllegalAccessError("ReadProcMemory returned 0!")
            }
            lastPointer =
                    Pointer(bufferMem.getInt(0L).toUInt().toLong()) //toUInt used due to sign issues explained earlier
        }
        var dataAddr = Pointer(Pointer.nativeValue(lastPointer) + offsets[offsets.size - 1])
        bufferMem = Memory(numBytes.toLong())
        if (ReadProcessMemory(GG_PROC!!.handle.pointer, dataAddr, bufferMem, numBytes, 0) == 0L) {
            throw IllegalAccessError("ReadProcMemory returned 0!")
        }
        return bufferMem.getByteBuffer(0L, numBytes.toLong())
    }

    override fun getXrdData() : List<PlayerData> {
        if(!isXrdConnected()){
            return ArrayList<PlayerData>()
        }
        var offs = longArrayOf(0x1C25AB4L, 0x44CL)
        var pDatas = ArrayList<PlayerData>()
        for (i in 0..7) {
            var bb = getByteBufferFromAddress(offs, 0x48)
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
            var dispname = String(dispbytes).trim('\u0000')
            /* if (steamid != 0L) {
                println("Steam ID: " + steamid.toString())
                println("Total Matches: " + totalmatch.toString())
                println("Number of wins: " + wins.toString())
                println("Display Name: " + dispname)
                println("Cab Id: " + cabid.toString())
                println("P1/P2 Id: " + playerside.toString())
                println("Character ID: 0x" + charid.toString(16))
                println("Loading percent: " + loadpercent.toString() + "%")
            } else {
                println("NO PLAYER")
            }*/
            var pd = PlayerData(dispname, steamid, charid, cabid, playerside, totalmatch, wins, loadpercent)
            pDatas.add(pd)
            offs[1] += 0x48L
        }
        return pDatas
    }
}