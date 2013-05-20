/**
 * 
 */
package XMars.Services.Common;

/**
 * @author Mars
 *
 */
public interface IMessageHandler  extends Runnable
{
	public abstract boolean SetMessageQueue(MessageQueue messageQueue);
	
}
