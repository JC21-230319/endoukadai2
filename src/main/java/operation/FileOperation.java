package operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;

public class FileOperation {
	//保存用のパス（コンテキストのパス）
	private final String IMAGEPATH="images/";
	private HttpServlet servlet;
	
	//コンストラクタ（サーブレットの情報を設定する）
	public FileOperation(HttpServlet servlet) {
		this.servlet=servlet;
	}
	
	//コンテキストからの相対パスを返却する
	public String getImagePath() {
		return IMAGEPATH;
	}
	
	//OSが参照する実際のパス（絶対パス）を返却する
	private String getRealPath() {
		return servlet.getServletContext().getRealPath(IMAGEPATH);
	}
	
	//ファイルの保存　フォーム送信によるマルチパート情報を引数で受け取る
	public void saveFile(Part part) {
		//送信時に設定したファイル名を取得
		String fileName=part.getSubmittedFileName();
		//OSで使うパスを使ってファイルを保存する位置も含めたファイル名を設定する
		String svFileName=getRealPath()+fileName;
		//保存
		try {
			File f=new File(getRealPath());
			if(!f.exists()) {
				f.mkdir();
			}
			part.write(svFileName);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//ファイルの一覧をList<String>で返す
	public List<String> getFiles(){
		//返却用のArraylListを準備
		List<String> list=new ArrayList<String>();
		//ファイルの一覧を取得
		File fileObject=new File(getRealPath());
		File[] files=fileObject.listFiles();
		if(files==null) {
			list=null;
		}else {
			for(File file:files) {
				list.add(file.getName());
			}
		}
		return list;
	}
	
	//ファイルの削除
	public void deleteFile(String[] files) {
		for(String file:files) {
			Path p=Paths.get(getRealPath()+file);
			try {
				Files.delete(p);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
