package XMars.Services.Common;
import java.util.*;

public class AsynWorkFlow implements IMessageHandler
{
	private Vector<IService> m_services;// = new Vector<IService>();
	
	private MessageQueue m_messageQueue = null;
	
	public boolean RegisterService(IService service)
	{
		return m_services.add(service);
	}
	
	Vector<IService> GetServices()
	{
		return m_services;
	}
	public void run() 
	{
		// TODO Auto-generated method stub
		try {
			m_messageQueue.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IMessage message = m_messageQueue.GetNewMessage();
		for(int i = 0; i < m_services.size(); i++)
		{
			((IService)m_services.get(i)).Process(message);
			
		}
	}
	
	public boolean SetMessageQueue(MessageQueue messageQueue) 
	{
		m_messageQueue = messageQueue;
		return true;
	}
	
}
