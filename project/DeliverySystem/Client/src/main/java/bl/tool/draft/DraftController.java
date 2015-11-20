package bl.tool.draft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;

import po.FormEnum;
import message.OperationMessage;
import vo.FormVO;

/** 
 * Client//bl.tool.draft//DraftController.java
 * @author CXWorks
 * @date 2015年11月19日 下午7:51:25
 * @version 1.0 
 */
public class DraftController implements DraftService {
	private static final String ROOT="draft/";
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	/* (non-Javadoc)
	 * @see bl.tool.draft.DraftService#saveDraft(vo.FormVO)
	 */
	@Override
	public OperationMessage saveDraft(FormVO vo) {
		try {
			writer=new ObjectOutputStream(new FileOutputStream(new File(ROOT+vo.getFormType().toString()+"/.2333")));
			writer.writeObject(vo);
			writer.close();
			return new OperationMessage();
		} catch (IOException e) {
			return new OperationMessage(false,e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see bl.tool.draft.DraftService#getDraft(po.FormEnum)
	 */
	@Override
	public FormVO getDraft(FormEnum formEnum) {
		try {
			reader=new ObjectInputStream(new FileInputStream(new File(ROOT+formEnum.toString()+"/.2333")));
			FormVO ans=(FormVO)reader.readObject();
			reader.close();
			return ans;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}

}