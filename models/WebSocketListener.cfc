component {
	
	public void function onOpen(struct data){

	}

	public void function onClose(struct data){

	}

	public string function onMessage(struct data){
		var clientId = data['clientID'];
		var message = data['message'];
	}

}


