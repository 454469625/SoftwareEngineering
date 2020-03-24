package wordCount;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 部分引用：https://www.cnblogs.com/yw0219/p/8047938.html，https://www.cnblogs.com/happyOwen/p/9646411.html
 *
 */

public class CountUtils {

    //实现-c功能 利用BufferedReader整行读取统计字符数
    public int countChar(String path){
        File file = new File(path);
        BufferedReader br = null;
        String line;
        int charCount = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    //Character 类用于对单个字符进行操作
                    //若不是空格，则字符数加1
                    if (!Character.isWhitespace(chars[i])) {
                        charCount++;
                    }
                }
            }
            System.out.println("the charCount is:" + charCount);
            br.close();
        } catch (Exception e) {
            System.out.println(path+"文件名错误");
        }
        return charCount;
    }


    //实现-w功能
    //统计英文单词数
    //先用Bufferedreader整行读取，然后添加到StringBuffer中，
    //将StringBuffer转化为字符串后，然后将非英文字符替换为空格，再根据空格分割
    public int countWord(String path) {
        BufferedReader br = null;
        String line;
        String[] strings;
        StringBuffer sbf = new StringBuffer();
        int wordCount = 0;
        String reg = "\\s+";
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
            }
            //将不是英文字母的字符替换成空格
            String s = sbf.toString().replaceAll("[^A-Za-z]", " ");
            strings = s.split(reg);
            wordCount = strings.length;
            System.out.println("the wordcount is;"+wordCount);
            br.close();
        }catch (Exception e){
            System.out.println(path+"文件名错误");
        }
        return wordCount;
    }

    //-l    统计总行数
    public int countLine(String path) {
        BufferedReader br = null;
        String line;
        int lineCount = 0;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                lineCount++;
            }
            System.out.println("the lineCount is:"+lineCount);
            br.close();
        }catch (Exception e){
            System.out.println(path+"文件名错误");
        }
        return lineCount;
    }

    //-a    统计注释行、空行、代码行
    public void countDiffline(String path) {
        int annotationLineCount = 0;
        int codeLineCount = 0;
        int spaceLineCount = 0;
        String line;
        BufferedReader br = null;
        //注释匹配器（匹配单行、多行、文档注释）
        Pattern annotationLinePattern = Pattern.compile("(//)|(/\\*)|(^\\s*\\*)|(^\\s*\\*+/)");
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (annotationLinePattern.matcher(line).find()) {
                    //注释行
                    annotationLineCount++;
                } else if (line.matches("\\s*\\p{Graph}?\\s*")) {
                    //空行
                    spaceLineCount++;
                } else {
                    codeLineCount++;
                }
            }
            System.out.println("the nullLineNum is:" + spaceLineCount);
            System.out.println("the annotationLineNum is:" + annotationLineCount);
            System.out.println("the codeLineNum is:" + codeLineCount);
            br.close();
        } catch (Exception e) {
            System.out.println(path+"文件名错误");
        }
    }

    //用ArrayList存储符合条件的文件的绝对路径
    public ArrayList<String> fileList = new ArrayList<String>();

    //递归获取符合条件的文件的绝对路径（在Main.java中已经对fileName进行通配符处理）
    public void getRecursionFiles(String dirName, String fileName) {
        File file = new File(dirName);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files!=null){
                for (File f : files) {
                    //当file1仍然是目录时，递归调用此函数
                    if (f.isDirectory()) {
                        getRecursionFiles(dirName+"\\"+f.getName(),fileName);
                    }else{
                        //处理后的fileName作为匹配规则，对遍历的文件进行匹配
                        //pattern 对象是一个正则表达式的编译表示。Pattern 类没有公共构造方法。
                        // 要创建一个 Pattern 对象，必须首先调用其公共静态编译方法，
                        // 它返回一个 Pattern 对象。该方法接受一个正则表达式作为它的第一个参数。
                        Pattern pattern = Pattern.compile(fileName);
                        //Matcher 对象是对输入字符串进行解释和匹配操作的引擎。Matcher 也没有公共构造方法。
                        // 需要调用 Pattern 对象的 matcher 方法来获得一个 Matcher 对象
                        Matcher m = pattern.matcher(f.getName());
                        if (m.matches()) {
                            fileList.add(dirName+"\\"+f.getName());
                        }
                    }
                }
            }
        }
    }

    //非递归获取符合条件的文件的绝对路径（在Main.java中已对fileName进行通配符处理）
    //仅获取当前目录下符合条件的文件，并将其绝对路径添加到ArrayList
    public void getFiles(String dirName, String fileName) {
        File file = new File(dirName);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        Pattern pattern = Pattern.compile(fileName);
                        Matcher m = pattern.matcher(f.getName());
                        if (m.matches()) {
                            fileList.add(dirName+"\\"+f.getName());
                        }
                    }
                }
            }
        }
    }
}
