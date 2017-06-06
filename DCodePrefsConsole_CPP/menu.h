#include <iostream>
#include <string>

#include "action.h" // This already includes console > resources

void m_file(){
	if(!c_cinEnpty()){
		string cmd = c_get();
		if(cmd == "create")
			a_file_create();
		else if(cmd == "--help")
			R.showArray(A_HELP_FILE);
		else
			R.showError(E_COMMAND_NOT_FOUND);
	} else
		R.showError(E_COMMAND_INCOMPLET);
}
