package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileUtils {
	public String homeDir;// = "E:"+split+"workSpace"+split+"tmp"+split;// 存放需要升级的文件列表文件, 文件名为paht.txt
	public String warDir;// = "wars"+split;// 存放war包的文件夹
	public String treeDir;// = "folders"+split;//存放升级文件
	public String webProjectDir;// = "D:/Oracle/Middleware/user_projects/domains/jxPPM_Domain/autodeploy/";
	//public String weblogicDomain = "d:"+split+"Oracle"+split+"Middleware"+split+"user_projects"+split+"domains"+split+"jxPPM_Domain"+split+"autodeploy"+split;// weblogic部署目录
	public String projectName;// = "PPMWeb"+split;
	public static String classPath = "WEB-INF/classes/" ;
	public String inText;// = "path.txt";
	//public static String batName = "cmd.bat";
	private static String inSplit = "@";// "|";
	private static String javaFlag = "src"+KeyValues.pathSplit;
	
	

	public FileUtils(String homeDir, String warDir, String treeDir, String webProjectDir, String projectName, String inText) throws Exception {
		this.homeDir = homeDir;
		this.warDir = warDir;
		this.treeDir = treeDir;
		this.webProjectDir = webProjectDir;
		this.projectName = projectName;
		this.inText = inText;
		File file = new File(homeDir);
		if (!file.exists()) {
			file.mkdir();
		}

	}

	// 从文本文件中读取文件路径
	public String[] readTxt() throws Exception {
		StringBuffer str = new StringBuffer("");
		String[] sts = null;
		FileInputStream fs = null;
		InputStreamReader isr = null;

		try {
			fs = new FileInputStream(homeDir + inText);
			isr = new InputStreamReader(fs);
			BufferedReader br = new BufferedReader(isr);

			String data = "";
			while ((data = br.readLine()) != null) {
				str.append(data + inSplit);
			}
			sts = str.toString().split(inSplit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fs.close();
			isr.close();
		}

		return sts;
	}

	// 创建目录
	public String createFolders(String path) throws Exception {
		String ret = "Error!";
		String fullPath = null;
		boolean succ = false;
		// 增加一层时间
		
		fullPath = treeDir + path;
		File file = new File(fullPath);
		if (!file.exists()) {
			succ = file.mkdirs();
		}
		ret = file.getAbsolutePath() + "\t\t\t" + succ + "\n";
		return ret;
	}

	// copy文件到目的目录
	public void copyFileTo(File srcFile, String desPath) throws Exception {
		int bytesum = 0;
		int byteread = 0;
		InputStream inStream = null;
		FileOutputStream fs = null;
		String fileName = srcFile.getName();

		try {
			if (srcFile.exists()) { // 文件存在时
				inStream = new FileInputStream(srcFile); // 读入原文件
				fs = new FileOutputStream(treeDir + desPath + fileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum = bytesum + byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inStream.close();
			fs.close();
		}
	}

	// 在path下查找满足nameRegex的文件列表
	public File[] findFile(String path, String nameRegex) throws Exception {
		File folder = new File(path);
		File[] files = null;
		if (folder.exists()) {
			final String desRegex = nameRegex;
			files = folder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.matches(desRegex);
				}
			});
		} else {
			Utils.printOnConsole(path + " not exists");
		}

		return files;
	}

	public String createPKG() throws Exception {
		String[] filePaths = this.readTxt(); // 需要打包的文件列表
		Map<String,Date> result = new HashMap<String, Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		String nowStr = sdf.format(new Date());
		String resultStr = null;
		
		String folder = null;
		String fileName = null;
		String desPath = null; // 目的文件夹相对路径
		String srcPath = null; // 源文件路径
		String folderPath = treeDir + nowStr + KeyValues.pathSplit;
		for (String filePath : filePaths) {
			if (filePath.indexOf(FileUtils.javaFlag) != -1) { //如果是src下面的文件, 则到weblogic目录下面拷贝class文件
				folder = filePath.substring(filePath.indexOf(FileUtils.javaFlag) + FileUtils.javaFlag.length(), filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				if (filePath.endsWith(".java")) {  
					fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1, filePath.lastIndexOf(".java")) + "(\\$.*)?.class"; // 需要拷贝的文件名. // $* 表示子类class文件
					desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + classPath + folder;
					srcPath = webProjectDir + projectName + KeyValues.pathSplit + classPath + folder; // 需要拷贝class文件的目录
				}else {//src下其他非java文件
					fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1); 
					desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + classPath + folder;
					srcPath = webProjectDir + projectName + KeyValues.pathSplit + classPath + folder; // 需要拷贝class文件的目录
				}
			}else{ // 其他文件直接拷贝
				folder = filePath.substring(filePath.indexOf(projectName+KeyValues.pathSplit) + projectName.length()+1, filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + folder;
				srcPath = webProjectDir + projectName + KeyValues.pathSplit + folder;
			}
			// 创建目的文件夹
			this.createFolders(desPath);

			// 查找文件
			File[] files = this.findFile(srcPath, fileName);

			// 复制文件到目的文件夹
			for (File file : files) {
				this.copyFileTo(file, desPath);
				// 打印信息
				result.put(treeDir + desPath + file.getName(), new Date(file.lastModified())); // 文件名 + 最后修改时间
			}

			
		}
		// 按文件名排序
		List<Map.Entry<String, Date>> sortList = new ArrayList<Map.Entry<String, Date>>(result.entrySet());
		Collections.sort(sortList, new Comparator<Map.Entry<String, Date>>() {
			public int compare(Entry<String, Date> o1,
					Entry<String, Date> o2) {
				String s1 = o1.getKey().substring(o1.getKey().lastIndexOf(KeyValues.pathSplit)+1);
				String s2 = o2.getKey().substring(o2.getKey().lastIndexOf(KeyValues.pathSplit)+1);
				return s1.compareToIgnoreCase(s2);
			}
		});
		StringBuffer printInfo = new StringBuffer();
		
		for(Map.Entry<String, Date> fileInfo : sortList) {
			String name = fileInfo.getKey();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lastModifiedDate = sdf2.format(fileInfo.getValue());
			printInfo.append(lastModifiedDate + "\t" + name + "\r\n");
		}
		
		

		// 打成war包
		String cmdStr= "jar -cMvf " + warDir + projectName + nowStr + ".war -C "+ folderPath + projectName + KeyValues.pathSplit +" .";
		//String cmdStr = "jar -cMvf E:/workSpace/tmp/wars/PPMWeb1111095205.war -C E:/workSpace/tmp/folders/1111095205/PPMWeb/ .";
		Runtime rt = Runtime.getRuntime();
		//Process ps = rt.exec(rootPath + batName);
		rt.exec("cmd /k start "+cmdStr);
		printInfo.append("A total of " + sortList.size() + " files");
		
		resultStr = printInfo.toString();
		return resultStr;
		
	}
}
