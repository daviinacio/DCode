#include "DCode.h"
#include <windows.h>

DCode::DCode(){

}

DCode::~DCode(){
	
}

// enCode and unCode
string DCode::enCode(string * in){
	
}

// Getters and Setters
int DCode::getMode(){ 
	return this->getMode(this->enCode(new string [2]{"", ""})); 
}
char DCode::getOpen(){ return this->open; }
char DCode::getSpace(){ return this->open; }
char DCode::getClose(){ return this->open; }

// Static methods

int DCode::getMode(string in){
	const char * _in = in.c_str();
	
	delete [] _in;
	
}

BOOL WINAPI DCode(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved){
	switch(fdwReason){
		case DLL_PROCESS_ATTACH:
			break;
		case DLL_PROCESS_DETACH:
			break;
		case DLL_THREAD_ATTACH:
			break;
		case DLL_THREAD_DETACH:
			break;
	}
	
	/* Return TRUE on success, FALSE on failure */
	return TRUE;
}
