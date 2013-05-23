package XMars.Services.Common;

public class ForkWorkflow implements IAsynWorkFlow{

	@Override
	public IMessage Process(IMessage message) {
		
		return null;
	}

	@Override
	public boolean RegisterService(IService service) {
		// TODO Auto-generated method stub
		return false;
	}

	public int ForkNumber = 0 ;
	private IService _service;
}
