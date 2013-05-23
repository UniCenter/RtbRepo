package XMars.Services.Common;
import java.util.*;

public class MessageQueue
{
	private Queue<IMessage> m_queue;// = new Queue<IMessage>();
	
	public IMessage GetNewMessage(){
		synchronized(m_queue)
		{
			return m_queue.poll();
		}
	}
}
