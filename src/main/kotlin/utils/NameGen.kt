package utils

import kotlin.random.Random

fun generateRandomName():String {
    var out = StringBuilder("${randStr(rn2).toUpperCase()}${randStr(rn3)}")
    var nameStep = 0
    for (i in 0..Random.nextInt(2,4)) { var part = StringBuilder()
        if (nameStep % 2 == 0) part.append("${randStr(rn2)}${randStr(rn3)}")
        else part.append("${randStr(rn1)}")
        when (Random.nextInt(15)) {
            1 -> part.append(" ${randStr(rn2).toUpperCase()}${randStr(rn3)}")
            2 -> part.append("_${randStr(rn2).toUpperCase()}${randStr(rn3)}")
            3 -> part.append("${randStr(rn3)}${randStr(rn2)}")
            4 -> part.append(Random.nextInt(10,1000))
            5 -> part = StringBuilder(part.toString().toUpperCase())
            6 -> part = StringBuilder(part.toString().toLowerCase())
            7 -> { val pick = Random.nextInt(part.length)
                part.replace(pick,pick+1, if (Random.nextBoolean()) part.get(pick).toString().toUpperCase() else part.get(pick).toString().toLowerCase()) }
            else -> part.append("")
        }
        nameStep++
        out.append(part)
    }
    return out.toString()
}

private val rn1 = arrayListOf("a","ze","st","ar","Koov","er","Te","chno","Lost","Ill","usion","isio","avi","La","bryz","Cath","at","icus","gry","phen","Soff","ish","Aoi","Mai","den","epo","ck","robo","sting","ray","sw","eet","X","jam","Tar","kus","Ev","ir","Dwa","jio","Big","bow","sa","TK","sha","dow","Del","rian","son","ny","wort","zik","Bon","bei","beez","uz","agri","guck","le","Jub","Kiz","zer","Day","men","dou","Pep","pery","Sp","lash","Kuro","gane","Ri","ven","Whoo","Boo","st","Whom","Sput","nik","Mk0","Cre","amy","Shits","Poo","Lord","Shin","Mun","chy","Mad","ao","Pan","Je","yu","dus","Sin","Pom","pa","dude","Riss","ay","Ja","yne","Mk1","Bea","Whi","Octo","pimp","Bon","bei","eez","us","Guck","le","oki","zeme","69","420","XxX","xXx","Seph","iroth","Sex","Haver","Weed","Pan","zee","boo","ties","Der","win","Sla","Elv","Sha","dow","Bla","ck","Sna","ke","Pru","sha","Cute","Miku","Rock","Man","Girl","Boy","Bitch","Bro","tato","seph","heim","Free","Wind","Jutsu","Ninja","obi","chan","kun","Kami","Poke","Kill","mon","Digi","Ahe","gao","Face","ken","dojo","Dead","State","God","Gren","dy","lici","ous","Love","Fire","Flame","Ice","Elf","Fair","Drag","Devil","Jin","Muge","Moog","Lock","Kara","Sol","Badguy","Jelly","Rich","Fake","Fraud","Pro","Sport","Spice","Butt","Blood","Evil","Goo","HUE","HUEHUE","HURR","HNNG","Gai","jen","Ota","ku","con","Fucker","Fast","Sonic","Blur","Red","Black","Green","Blue","Pink","White","Macha","mito","Anji","Flash","Shad","Eden","War","ior","Priest","ess","Fal","len","Ang","ing","est","ery"," the ","Fl","ip","up","Throw","Tier","Nerf")
private val rn2 = arrayListOf("b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","z")
private val rn3 = arrayListOf("a","e","i","o","u","a","e","i","o","u","y")
private fun randStr(rn: ArrayList<String>) = rn[Random.nextInt(rn.size)]