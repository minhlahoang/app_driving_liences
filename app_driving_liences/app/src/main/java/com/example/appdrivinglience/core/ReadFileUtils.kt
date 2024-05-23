package com.example.appdrivinglience.core

import android.content.Context
import com.example.appdrivinglience.database.dao.QuestionDao
import com.example.appdrivinglience.database.dao.TrickDao
import com.example.appdrivinglience.database.model.Question
import com.example.appdrivinglience.database.model.SignModel
import com.example.appdrivinglience.feature.trick_screen.TrickModel
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class ReadFileUtils @Inject constructor(
    private val context: Context,
    private val trickDao: TrickDao,
    private val questionDao: QuestionDao
    ) {
    fun readTrickFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("TRICK/meothi.txt")));
        var data : String = bufferReader.readLine()
        var title : String = data
        var content: String = ""
        while (data.isNotEmpty()) {
            runCatching {
                data = bufferReader.readLine()
                if (data[0] == '*'){
                    val trickModel = TrickModel(title = title, content =  content)
                    println("trickModel = $trickModel")
                    trickDao.insertTrick(trickModel)
                    title = data
                    content = ""
                }else {
                    content += data+"\n"
                }
            }.onFailure {
                return
            }

        }
    }
    fun readVHTheoryFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("VANHOA_DAODUC/vanhoa_daoduc.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt,dataList,3)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 3)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readCTSCTheoryFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("CAUTAO_SUACHUA/cautao_suachua.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt ,dataList,5)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 5)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readNVVTTheoryFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("NGHIEPVU_VANTAI/nghiepvu_vantai.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt ,dataList,4)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 4)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readKNQTFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("KHAI_NIEM_QUY_TAC/khainiemquytac.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt,dataList,2)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 2)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readDLFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("DIEM_LIET/diemliet.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt ,dataList,1)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 1)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readSHFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("SA_HINH/sahinh.txt")));
        var data : String? = bufferReader.readLine()

        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(1,dataList,7)
        var cnt = 1
        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 7)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun readBanSignFile(context: Context): List<SignModel>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("HOCBIENBAO/bienbaocam.txt")));
        var data : String? = bufferReader.readLine()
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val signModelList = mutableListOf<SignModel>()
        signModelList.add(SignModel(dataList[0], dataList[1], dataList[2]))

        while (data?.isNotEmpty() == true ){
            runCatching {

                data = bufferReader.readLine()
                if (data == null) return signModelList
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                if (dataTmpList.isNotEmpty() && dataTmpList.size == 3){
                    signModelList.add(SignModel(dataTmpList[0], dataTmpList[1], dataTmpList[2]))
                }
            }.onFailure {
                println(signModelList)

                return signModelList
            }
        }
        println(signModelList)
        return signModelList
    }
    fun readDangerousSignFile(context: Context): List<SignModel>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("HOCBIENBAO/bienbaonguyhiem.txt")));
        var data : String? = bufferReader.readLine()
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val signModelList = mutableListOf<SignModel>()
        signModelList.add(SignModel(dataList[0], dataList[1], dataList[2]))

        while (data?.isNotEmpty() == true ){
            runCatching {

                data = bufferReader.readLine()
                if (data == null) return signModelList
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                if (dataTmpList.isNotEmpty() && dataTmpList.size == 3){
                    signModelList.add(SignModel(dataTmpList[0], dataTmpList[1], dataTmpList[2]))
                }
            }.onFailure {
                return signModelList
            }
        }
        return signModelList
    }
    fun readTempSignFile(context: Context): List<SignModel>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("HOCBIENBAO/bienbaophu.txt")));
        var data : String? = bufferReader.readLine()
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val signModelList = mutableListOf<SignModel>()
        signModelList.add(SignModel(dataList[0], dataList[1], dataList[2]))

        while (data?.isNotEmpty() == true ){
            runCatching {

                data = bufferReader.readLine()
                if (data == null) return signModelList
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                if (dataTmpList.isNotEmpty() && dataTmpList.size == 3){
                    signModelList.add(SignModel(dataTmpList[0], dataTmpList[1], dataTmpList[2]))
                }
            }.onFailure {
                return signModelList
            }
        }
        return signModelList
    }
    fun readGuideSignFile(context: Context): List<SignModel>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("HOCBIENBAO/bienchidan.txt")));
        var data : String? = bufferReader.readLine()
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val signModelList = mutableListOf<SignModel>()
        signModelList.add(SignModel(dataList[0], dataList[1], dataList[2]))

        while (data?.isNotEmpty() == true ){
            runCatching {

                data = bufferReader.readLine()
                if (data == null) return signModelList
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                if (dataTmpList.isNotEmpty() && dataTmpList.size == 3){
                    signModelList.add(SignModel(dataTmpList[0], dataTmpList[1], dataTmpList[2]))
                }
            }.onFailure {
                return signModelList
            }
        }
        return signModelList
    }
    fun readControlSignFile(context: Context): List<SignModel>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("HOCBIENBAO/bienhieulenh.txt")));
        var data : String? = bufferReader.readLine()
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val signModelList = mutableListOf<SignModel>() // listo
        signModelList.add(SignModel(dataList[0], dataList[1], dataList[2]))

        while (data?.isNotEmpty() == true ){
            runCatching {

                data = bufferReader.readLine()
                if (data == null) return signModelList
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                if (dataTmpList.isNotEmpty() && dataTmpList.size == 3){
                    signModelList.add(SignModel(dataTmpList[0], dataTmpList[1], dataTmpList[2]))
                }
            }.onFailure {
                return signModelList
            }
        }
        return signModelList
    }
    fun readBBFile(context: Context){
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("BIEN_BAO/bienbao.txt")));
        var data : String? = bufferReader.readLine()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt,dataList,6)

        questionData?.let {
            questionDao.insertQuestion(it);
        }

        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                if (data == null) return
                cnt++
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt,dataTmpList, 6)?.let {
                    questionDao.insertQuestion(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return
            }
        }
    }
    fun getNumberTest(folder: String) : Int {
        return context.assets.list(folder)?.size ?: 0
    }
    fun getInformationTest(folder: String ,file: String):List<Question>{
        val bufferReader = BufferedReader(InputStreamReader(context.assets.open("$folder/$file")));
        var data : String? = bufferReader.readLine()
        val questionList = mutableListOf<Question>()
        var cnt = 1
        val dataList = data?.split("@@")?.toList() ?: mutableListOf();
        val questionData = getQuestion(cnt ,dataList,6)
        questionData?.let {
            questionList.add(it)
        }
        while (data?.isNotEmpty() == true) {
            runCatching {
                data = bufferReader.readLine()
                cnt++
                if (data == null) return questionList;
                val dataTmpList = data?.split("@@")?.toList() ?: mutableListOf()
                getQuestion(cnt ,dataTmpList, 6)?.let {
                    questionList.add(it)
                }

            }.onFailure {
                println("error = ${it.message}")
                return questionList
            }
        }
        return  questionList
    }
    private fun getQuestion(index: Int , dataList: List<String>, idCategoryQuestion: Long) : Question? {
        var dataQuestion : Question?= null
         if (dataList.size >= 7 ){
             val isHttp = dataList[0].contains("http")
             var imgUrl = ""
             if (isHttp){
                 imgUrl = dataList[0].substring(dataList[0].indexOf("http"))
             }

            dataQuestion =  Question(
                idCategoryQuestion = idCategoryQuestion,
                question = "Câu $index: "+dataList[0].substring(dataList[0].indexOfFirst { it ==':'}+1),
                ansA = dataList[1],
                ansB = dataList[2],
                ansC = dataList[3],
                ansD = dataList[4],
                imageUrl = imgUrl,
                correctAns = getAnswerQuestion(dataList[1], dataList[2], dataList[3], dataList[4], dataList[5]),
                analysis = dataList[6],
                isImportant = dataList[0][0] == '*'
            )
        }else if(dataList.size == 6){
             val isHttp = dataList[0].contains("http")
             var imgUrl = ""
             if (isHttp){
                 imgUrl = dataList[0].substring(dataList[0].indexOf("http"))
             }
            dataQuestion =  Question(
                idCategoryQuestion = idCategoryQuestion,
                question = "Câu $index: "+dataList[0].substring(dataList[0].indexOfFirst { it ==':'}+1),
                ansA = dataList[1],
                ansB = dataList[2],
                ansC = dataList[3],
                imageUrl = imgUrl,
                correctAns = getAnswerQuestion(a= dataList[1], b=dataList[2], c=dataList[3],d = null, ans = dataList[4]),
                analysis = dataList[5],
                isImportant = dataList[0][0] == '*'
            )
        }else if(dataList.size == 5) {
             val isHttp = dataList[0].contains("http")
             var imgUrl = ""
             if (isHttp){
                 imgUrl = dataList[0].substring(dataList[0].indexOf("http"))
             }
             dataQuestion =  Question(
                 idCategoryQuestion= idCategoryQuestion,
                 question = "Câu $index: "+dataList[0].substring(dataList[0].indexOfFirst { it ==':'}+1),
                 ansA = dataList[1],
                 ansB = dataList[2],
                 imageUrl = imgUrl,
                 correctAns = getAnswerQuestion(dataList[1], dataList[2], c= null, d = null, dataList[3]),
                 analysis = dataList[4],
                 isImportant = dataList[0][0] == '*'
            )
        }else {
             print("size = ${dataList.size}")
        }
        println(dataQuestion)
        return  dataQuestion
    }
    private fun getAnswerQuestion(a: String, b : String, c : String?, d: String?, ans: String): Int {
        return if (a.contains(ans)) {
            0
        }else if (b.contains(ans) ){
            1
        }else if(c?.contains(ans) == true) {
            2
        }else{
            3
        }
    }


}