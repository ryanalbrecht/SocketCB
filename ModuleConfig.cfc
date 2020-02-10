component {
    
    this.name = "socketCB";
    this.author = "";
    this.webUrl = "https://github.com/ryanalbrecht/socketCB";
	this.description 		= "";
	this.version			= "1.0.0";
	// If true, looks for views in the parent first, if not found, then in the module. Else vice-versa
	this.viewParentLookup 	= true;
	// If true, looks for layouts in the parent first, if not found, then in module. Else vice-versa
	this.layoutParentLookup = true;
	// Module Entry Point
	this.entryPoint			= "socketcb";
	// Inherit Entry Point
	this.inheritEntryPoint 	= false;
	// Model Namespace
	this.modelNamespace		= "socketcb";
	// CF Mapping
	this.cfmapping			= "socketcb";
	// Auto-map models
	this.autoMapModels		= true;
	// Module Dependencies
	this.dependencies 		= ["cbjavaloader"];

    function configure() {

		// Module Settings		
  		settings = {
  			host 		= 'localhost',
  			port 		= 8989,
  			libPath 	= modulePath & "/lib"
  		};

		routes = [
			{pattern="/", handler="control", action="index"}
		];

    }

    function onLoad(){

		wireBox.getInstance( "loader@cbjavaloader" ).appendPaths( settings.libPath );
    }
}