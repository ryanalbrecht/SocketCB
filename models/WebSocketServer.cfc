component singleton accessors="true" {

	property name="webSocketServer";
	property name="sUuid";
	property name="cUuid";

	/**
	* @javaloader.inject loader@cbjavaloader
	* @controller.inject coldbox
	* @settings.inject coldbox:modulesettings:socketcb
	*/
	function init(javaloader, controller, settings){
		var socketListener = controller.getWirebox().getInstance('WebSocketListener@socketcb');
		var proxyWsl = createDynamicProxy(socketListener, ['socket.cb.SocketListener']);
		var host = javacast('string', settings.host);
		var port = javacast('int', settings.port);

		try{
			variables.webSocketServer =  createObject('java', 'socket.cb.Server').init(local.host, local.port, local.proxyWsl);	
			variables.cUuid = createUUID();
		} catch (any e){
			writeDump(e);
			abort;
		}
		
	}




	function start(){
		variables.webSocketServer.start();
	}

	function stop(){
		variables.webSocketServer.stop();
	}

	function broadcast(message){
		variables.webSocketServer.broadcast(message);
	}

	function getSUuid(){
		return uCase(variables.webSocketServer.getUuid());
	}

}