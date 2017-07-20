#include <iostream>
#include <windows.h>
#include <string>

#include "menu.h"	// This already includes action > console > resources

//#include <cstdlib>
//#include <stdio.h>
//#include <stlib.h>

//#include "DCode.h"

//extern "C" __declspec(dllimport) void InstallHook();

using namespace std;

string diretory = "C:\\Users\\Davi\\Desktop";

//extern "C" __declspec(dllimport) DllMain(HINSTANCE hInst, DWORD reason, LPVOID reserved)

int main(int argc, char** argv) {
	console_default_color = color_lightGray;
	
	c_cabecalho();
	
	while(1){
		cout << endl;
		cc_print(color_cyan, diretory);
		string cmd = cc_get(color_white, S_CURSOR);
		/*Sleep(100);
		if(GetAsyncKeyState(VK_CONTROL)){
			cout << "Enter" << endl;
		}*/
		
		if(cmd == "line"){
			if(c_cinEnpty())
				cout << "Empty" << endl;
			else
				cout << "Not empty" << endl;
		} else
		if(cmd == "help")
			R.showArray(A_HELP);
		else if(cmd == "clear")
			c_cabecalho();
		else if(cmd == "file")
			m_file();
		else if(cmd == "exit" || cmd == "close")
			return 0;
		else if(cmd == ""){}
		else
			R.showError(E_COMMAND_NOT_FOUND);
	}
	
	return 0;
}
