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
	public String homeDir;// = "E:"+split+"workSpace"+split+"tmp"+split;// �����Ҫ�������ļ��б��ļ�, �ļ���Ϊpaht.txt
	public String warDir;// = "wars"+split;// ���war�����ļ���
	public String treeDir;// = "folders"+split;//��������ļ�
	public String webProjectDir;// = "D:/Oracle/Middleware/user_projects/domains/jxPPM_Domain/autodeploy/";
	//public String weblogicDomain = "d:"+split+"Oracle"+split+"Middleware"+split+"user_projects"+split+"domains"+split+"jxPPM_Domain"+split+"autodeploy"+split;// weblogic����Ŀ¼
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

	// ���ı��ļ��ж�ȡ�ļ�·��
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

	// ����Ŀ¼
	public String createFolders(String path) throws Exception {
		String ret = "Error!";
		String fullPath = null;
		boolean succ = false;
		// ����һ��ʱ��
		
		fullPath = treeDir + path;
		File file = new File(fullPath);
		if (!file.exists()) {
			succ = file.mkdirs();
		}
		ret = file.getAbsolutePath() + "\t\t\t" + succ + "\n";
		return ret;
	}

	// copy�ļ���Ŀ��Ŀ¼
	public void copyFileTo(File srcFile, String desPath) throws Exception {
		int bytesum = 0;
		int byteread = 0;
		InputStream inStream = null;
		FileOutputStream fs = null;
		String fileName = srcFile.getName();

		try {
			if (srcFile.exists()) { // �ļ�����ʱ
				inStream = new FileInputStream(srcFile); // ����ԭ�ļ�
				fs = new FileOutputStream(treeDir + desPath + fileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum = bytesum + byteread; // �ֽ��� �ļ���С
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

	// ��path�²�������nameRegex���ļ��б�
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
		String[] filePaths = this.readTxt(); // ��Ҫ������ļ��б�
		Map<String,Date> result = new HashMap<String, Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		String nowStr = sdf.format(new Date());
		String resultStr = null;
		
		String folder = null;
		String fileName = null;
		String desPath = null; // Ŀ���ļ������·��
		String srcPath = null; // Դ�ļ�·��
		String folderPath = treeDir + nowStr + KeyValues.pathSplit;
		for (String filePath : filePaths) {
			if (filePath.indexOf(FileUtils.javaFlag) != -1) { //�����src������ļ�, ��weblogicĿ¼���濽��class�ļ�
				folder = filePath.substring(filePath.indexOf(FileUtils.javaFlag) + FileUtils.javaFlag.length(), filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				if (filePath.endsWith(".java")) {  
					fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1, filePath.lastIndexOf(".java")) + "(\\$.*)?.class"; // ��Ҫ�������ļ���. // $* ��ʾ����class�ļ�
					desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + classPath + folder;
					srcPath = webProjectDir + projectName + KeyValues.pathSplit + classPath + folder; // ��Ҫ����class�ļ���Ŀ¼
				}else {//src��������java�ļ�
					fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1); 
					desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + classPath + folder;
					srcPath = webProjectDir + projectName + KeyValues.pathSplit + classPath + folder; // ��Ҫ����class�ļ���Ŀ¼
				}
			}else{ // �����ļ�ֱ�ӿ���
				folder = filePath.substring(filePath.indexOf(projectName+KeyValues.pathSplit) + projectName.length()+1, filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				fileName = filePath.substring(filePath.lastIndexOf(KeyValues.pathSplit) + 1);
				desPath = nowStr + KeyValues.pathSplit + projectName + KeyValues.pathSplit + folder;
				srcPath = webProjectDir + projectName + KeyValues.pathSplit + folder;
			}
			// ����Ŀ���ļ���
			this.createFolders(desPath);

			// �����ļ�
			File[] files = this.findFile(srcPath, fileName);

			// �����ļ���Ŀ���ļ���
			for (File file : files) {
				this.copyFileTo(file, desPath);
				// ��ӡ��Ϣ
				result.put(treeDir + desPath + file.getName(), new Date(file.lastModified())); // �ļ��� + ����޸�ʱ��
			}

			
		}
		// ���ļ�������
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
		
		

		// ���war��
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
