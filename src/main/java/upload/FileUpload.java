package upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import operation.FileOperation;
/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/upload")
@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FileOperation operation;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
        operation=new FileOperation(this);
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//ファイル一覧を取得してfile.japへ遷移する
		List<String> files=new ArrayList<String>();
		files=operation.getFiles();
		
		request.setAttribute("PATH", operation.getImagePath());
		request.setAttribute("FILES",files);
		request.getRequestDispatcher("./jsp/file.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//アップロードされたファイルを保存する
		operation.saveFile(request.getPart("upfile"));
		
		doGet(request, response);
	}
}
