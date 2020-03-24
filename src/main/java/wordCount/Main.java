package wordCount;

import java.io.*;
import java.util.ArrayList;
public class Main{
        private static boolean isCountchar = false;
        private static boolean isCountword = false;
        private static boolean isCountline = false;
        private static boolean isRecursion = false;
        private static boolean isCountdiffline = false;
        //利用arraylist存储符合条件的文件的绝对路径
        private static ArrayList<String> fileList = new ArrayList<String>();

        public static void main(String[] args) throws IOException,ArrayIndexOutOfBoundsException{
            //此处args似乎越界了也许我应该给它分配一个空间？
            //args = new String[6];
            //运行报错了但是测试可以通过
            //默认最后一个参数为路径名
            String path = args[args.length-1];
            CountUtils countUtils = new CountUtils();
            //判断需要执行的功能
            for(int i=0; i<args.length-1; i++){
                if(args[i].equals("-c")){
                    isCountchar = true;
                }
                if(args[i].equals("-w")){
                    isCountword = true;
                }
                if(args[i].equals("-l")){
                    isCountline = true;
                }
                if(args[i].equals("-s")){
                    isRecursion = true;
                }
                if(args[i].equals("-a")){
                    isCountdiffline = true;
                }
            }
            //获取目录名
            //split 里面的参数是正则表达式，在Java里面用字符串表示正则表达式时，
            //反斜杠是转义符，表示一个反斜杠时，要在前面加一个斜杠，即 \\ 表示一个斜杠。
            //所以此处split中的\\\\表示\\用于分割文件路径
            //此处的一系列操作是为了获取所给文件路径的目录，即将路径最后的文件名删去
            String paths[] = path.split("\\\\");
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<paths.length-1; i++){
                if(i==paths.length-2){
                    sb.append(paths[i]);
                }else{
                    sb.append(paths[i] + "\\");
                }
            }

            String dirName = sb.toString();
            //File(String pathname)通过给定路径名字符串转换为抽象路径名来创建一个新File实例
            File file = new File(dirName);
            if (!file.isDirectory()){
                System.out.println("路径错误！");
            }
            String fileName = paths[paths.length - 1];
            //对文件名通配符处理
            //replaceAll() 方法使用给定的参数 replacement 替换字符串所有匹配给定的正则表达式的子字符串。
            //在 Java中，\\ 表示：我要插入一个正则表达式的反斜线，所以其后的字符具有特殊的意义
            fileName = fileName.replaceAll("\\*","\\.+").replaceAll("\\?","\\.");

            //若IS_RECURSION,则使用递归获取文件名（包括子目录），否则只获取当前目录下符合条件的文件名
            if (isRecursion){
                countUtils.getRecursionFiles(dirName, fileName);
            }else{
                countUtils.getFiles(dirName,fileName);
            }
            fileList = countUtils.fileList;

            //遍历fileList,对每一个文件使用选择的功能
            for (String fList :
                    fileList) {
                System.out.println("文件路径为："+fList);
                if (isCountchar){
                    countUtils.countChar(fList);
                }
                if (isCountword){
                    countUtils.countWord(fList);
                }
                if (isCountline){
                    countUtils.countLine(fList);
                }
                if (isCountdiffline){
                    countUtils.countDiffline(fList);
                }
            }
        }
}