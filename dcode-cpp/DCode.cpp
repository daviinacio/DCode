#include "dcode.h"
#include <windows.h>

using namespace std;

// Constructors
DCode::DCode(){
	MessageBox(0, "Hello World from DLL!\n","Hi",MB_ICONINFORMATION);
	
	cout << DCode::NORMAL << endl;
}

DCode::DCode(DCodeMode mode){
	switch(mode){
		case NORMAL: this->open = '<'; this->space = ';'; this->close = '>'; break;
		case ARRAY: this->open = '['; this->space = ':'; this->close = ']'; break;
		case FILE: this->open = '{'; this->space = '_'; this->close = '}'; break;
		case DATE: this->open = '('; this->space = '/'; this->close = ')'; break;
		case SMALL: this->open = '.'; this->space = ','; this->close = ';'; break;
	}
}

// Destructors
DCode::~DCode(){
	
}

// Functions

// enCode and unCode
string DCode::enCode(list<string> input){
	
}

list<string> DCode::unCode(string input){
	
}


// DLL Functions
BOOL WINAPI DCode(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved){
	switch(fdwReason){
		case DLL_PROCESS_ATTACH: break; 
		case DLL_PROCESS_DETACH: break; 
		case DLL_THREAD_ATTACH: break; 
		case DLL_THREAD_DETACH: break;
	}
	
	/* Return TRUE on success, FALSE on failure */
	return TRUE;
}
