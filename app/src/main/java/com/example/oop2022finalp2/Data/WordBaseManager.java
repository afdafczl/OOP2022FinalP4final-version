package com.example.oop2022finalp2.Data;


import android.os.Environment;

import com.example.oop2022finalp2.MainActivity;
import com.example.oop2022finalp2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author : Zhilong Cao
 * @description: store as "WordBaseManager" in getExternalStorageDirectory, Contains WordBases and methods to get and add WordBases
 * @date :2022/11/19 23:49
 **/
public class WordBaseManager implements Serializable {

    /**
     * @description: contains different WordBase, with WordMap, key is WordBaseId.
     * @date :2022/11/19 23:49
     **/
    private static WordBaseManager wordBaseManager = null;

    private TestBase testBase = null;

    public static void setTestBase(TestBase testBase) {
        testBase = testBase;
    }
    public TestBase getTestBase() {
        return testBase;
    }

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/19 23:49
     * @return :if Object exist in local file return this object, if Object not exist in local file,return new Object
     **/
    public static WordBaseManager getWordBaseManager()
    {
        if (wordBaseManager == null)
        {
            //create local Object file
            try
            {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(path+"/WordBaseManager");

                // if file not exist, serialize this object
                if (!file.exists())
                {
                    file.createNewFile();
                    OutputStream ops=new FileOutputStream(file);

                    ObjectOutputStream oos=new ObjectOutputStream(ops);

                    wordBaseManager = new WordBaseManager();
                    wordBaseManager.storePath = file;

                    //generate TestBase if not exist
                    IsTestBaseFactory isTestBaseFactory = TestBase.getTestBaseFactory();
                    TestBase testBase_tmp = isTestBaseFactory.creTestBase();
                    testBase_tmp.TestBaseId = 114514;
                    testBase_tmp.TestBaseName = "kiminowadaxiwaTestBase";

                    wordBaseManager.testBase = testBase_tmp;

                    oos.writeObject(wordBaseManager);
                    oos.close();
                    ops.close();
                }
                //if file exist, deserialize this file to Object
                else
                {
                    try
                    {
                        InputStream in = new FileInputStream(file);

                        ObjectInputStream input = new ObjectInputStream(in);

                        wordBaseManager = (WordBaseManager) input.readObject();

                        input.close();
                        in.close();
                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return wordBaseManager;
    }

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/19 23:49
     * @return :update WordBaseManager, should be used when WordBase is changed.(adding/removing words, etc)
     **/
    public static void setWordBaseManager()
    {
        try
        {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path+"/WordBaseManager");
            if (file.exists())
            {
                file.delete();
            }
            OutputStream ops=new FileOutputStream(file);

            ObjectOutputStream oos=new ObjectOutputStream(ops);

            oos.writeObject(wordBaseManager);
            oos.close();
            ops.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public File storePath = null;

    private WordBase selectWordBase = null;

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/19 23:49
     *
     * @return :return default WordBase with id 114514, if not exist create one
     **/
    public static WordBase getDefaultWordBase()
    {
        WordBase wordBase_default = wordBaseManager.getWordBase(114514);


        if (null == wordBase_default)
        {
            IsWordBaseFactory factory = WordBase.getWordBaseFactory();
            wordBase_default = factory.creWordBase(114514,"default");
            wordBaseManager.addWordBase(114514, wordBase_default);
            //初始化default,这里加入了30个hello1,hello2....
            //创建官方WordBase,第一次运行该app才会执行,因为只有第一次运行时114514不存在
            //CET4

            //添加仓库
            generateOfficalWordbase(MainActivity.inputStream,"CET-4",1);
            generateOfficalWordbase(MainActivity.inputStreamCet6,"CET-6",2);
            generateOfficalWordbase(MainActivity.inputStreamGre,"GRE",3);


            /*Word tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(1,tmpWord);
            tmpWord=Word.getWordFactory().creWord("aboard","adv.在（船）车上");
            wordBase_CET4.addWord(2,tmpWord);
            tmpWord=Word.getWordFactory().creWord("absolute","adj.绝对的");
            wordBase_CET4.addWord(3,tmpWord);
            tmpWord=Word.getWordFactory().creWord("absorb","vt.吸收；使专心");
            wordBase_CET4.addWord(4,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abstract","n.摘要");
            wordBase_CET4.addWord(5,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(6,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(7,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(8,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(9,tmpWord);
            tmpWord=Word.getWordFactory().creWord("abandon","vt.丢弃；放弃");
            wordBase_CET4.addWord(10,tmpWord);
            */
            //update old WordBaseManager
            WordBaseManager.setWordBaseManager();
        }
        return wordBase_default;
    }


    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/19 23:49
     * @return :return selected WordBase, return null if not selected
     **/
    public WordBase getSelectWordBase()
    {
        return selectWordBase;
    }

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/19 23:49
     **/
    public void setSelectWordBase(WordBase wordBase){
        selectWordBase=wordBase;
    }


    public ArrayList<Integer> WordBaseIDs = new ArrayList<Integer>();

    public TreeMap<Integer,WordBase> WordBaseGroup = new TreeMap<Integer, WordBase>();

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/17 14:02
     * @return :return WordBase if exist ,return null if not exist
     **/
    public WordBase getWordBase(Integer integer)
    {
        if (WordBaseGroup.get(integer) == null)
        {
            return null;
        }
        return WordBaseGroup.get(integer);
    }

    /**
     * @author Zhilong Cao
     * @description:
     * @date :2022/11/17 14:02
     * @return : Return true if add success. Return false if already exist.
     **/
    public Boolean addWordBase(Integer integer,WordBase wordBase)
    {
        if (WordBaseGroup.get(integer) != null)
        {
            return false;
        }
        WordBaseGroup.put(integer,wordBase);
        WordBaseIDs.add(integer);
        return true;
    }

    /**
     * @author Fanyu Xia
     * @description:获得问题
     * @date :2022/12/10 15:43
     **/
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<Question>();
        WordBaseManager wordBaseManager = WordBaseManager.getWordBaseManager();
        WordBase wordBase_test = wordBaseManager.getSelectWordBase();
        TreeMap<Integer,Word> test_word_map = new TreeMap();
        test_word_map = wordBase_test.WordMap;
        Random rand = new Random();
        int i=0;
        for(Word value:test_word_map.values()){
            i++;
            if(value.getProficiency()!=2&&i>1&&i<test_word_map.size()-1) {
                Question question = new Question();
                question.ID = i;
                question.question = value.english;
                question.answerA = test_word_map.get(rand.nextInt(test_word_map.size())+1).chinese;
                question.answerB = value.chinese;
                question.answerC = test_word_map.get(rand.nextInt(test_word_map.size())+1).chinese;
                question.answerD = test_word_map.get(rand.nextInt(test_word_map.size())+1).chinese;
                question.answer = 1;
                question.explaination = "无";
                //表示没有选择任何选项
                question.selectedAnswer = -1;
                list.add(question);
            }
            else if(i==1){
                Question question = new Question();
                question.ID = i;
                question.question = value.english;
                question.answerA = test_word_map.get(i+1).chinese;
                question.answerB = value.chinese;
                question.answerC = test_word_map.get(i+2).chinese;
                question.answerD = test_word_map.get(i+3).chinese;
                question.answer = 1;
                question.explaination = "无";
                //表示没有选择任何选项
                question.selectedAnswer = -1;
                list.add(question);
            }
            else if(i==test_word_map.size()-1){
                Question question = new Question();
                question.ID = i;
                question.question = value.english;
                question.answerA = test_word_map.get(i-1).chinese;
                question.answerB = value.chinese;
                question.answerC = test_word_map.get(i+1).chinese;
                question.answerD = test_word_map.get(1).chinese;
                question.answer = 1;
                question.explaination = "无";
                //表示没有选择任何选项
                question.selectedAnswer = -1;
                list.add(question);
            }
            else if(i==test_word_map.size()){
                Question question = new Question();
                question.ID = i;
                question.question = value.english;
                question.answerA = test_word_map.get(i-1).chinese;
                question.answerB = value.chinese;
                question.answerC = test_word_map.get(i-2).chinese;
                question.answerD = test_word_map.get(i-3).chinese;
                question.answer = 1;
                question.explaination = "无";
                //表示没有选择任何选项
                question.selectedAnswer = -1;
                list.add(question);
            }
        }
        return list;
    }

    /**
     * @author Fanyu Xia
     * @description:生成随机中文释义
     * @date :2022/12/20 22:13
     **/
    public String Chinese_random(){
        String str = "";
        String str2 = "";
        int hightPos,lowPos;//定义高低位
        Random random = new Random();

        hightPos=(176 + Math.abs(random.nextInt(39)));
            lowPos =(161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
        b[0] =(new Integer(hightPos).byteValue());
        b[1] =(new Integer(lowPos).byteValue());
        try{
        str = new String(b,"GBK");
            str2 = Character.toString((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
            str=str+str2;
             }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;

    }


    /**
     * @author Zhilong Cao
     * @description: need to make sure ActResource is get in MainActivity
     * @date :2022/12/10 13:14
     *
     **/
    public static WordBase generateOfficalWordbase(InputStream in,String name,int id)
    {
        IsWordBaseFactory factory = WordBase.getWordBaseFactory();
        WordBase wordBase = factory.creWordBase(id,name);
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;



        try
        {

            inputStreamReader = new InputStreamReader(in,"utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = bufferedReader.readLine();
            int index = 1;
            while ( str != null)
            {

                String[] strs = str.split(" ");
                Word tmpWord=Word.getWordFactory().creWord(strs[0],strs[1]);
                wordBase.addWord(index,tmpWord);
                index++;
                str = bufferedReader.readLine();
            }
            wordBaseManager.addWordBase(id, wordBase);

            bufferedReader.close();
            inputStreamReader.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return wordBase;
    }

}
