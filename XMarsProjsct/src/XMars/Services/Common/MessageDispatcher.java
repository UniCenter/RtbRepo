package XMars.Services.Common;
import java.util.*;

public class MessageDispatcher implements IAsynService {
	private Vector<IAsynWorkFlow> m_workflows;
	
	public MessageDispatcher(){
		m_workflows = new Vector<IAsynWorkFlow>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMessageState Invoke(IMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean Process(IMessage message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean SetMessageQueue(MessageQueue messageQueue) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
