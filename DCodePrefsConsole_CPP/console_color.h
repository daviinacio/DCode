#include <iostream>
#include <string>

#define color_darkBlue 1
#define color_darkGreen 2
#define color_darkCyan 3
#define color_darkRed 4
#define color_purple 5
#define color_darkYellow 6
#define color_lightGray 7
#define color_darkGray 8
#define color_blue 9
#define color_green 10
#define color_cyan 11
#define color_red 12
#define color_pink 13
#define color_yellow 14
#define color_white 15

using namespace std;

int console_default_color = color_lightGray;

void cc_print(int color, string text){
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, color);
	cout << text;
	SetConsoleTextAttribute(hConsole, console_default_color);
}

void cc_printLoop(int color, int position, int length, string text){
	int dig = position / 10;
	for(int i = dig; i < (length - 1); i++){
		cout << text;
	}
}

void cc_print(int color, int num, int digits){
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, color);
	cc_printLoop(color, num, digits, "0");
	cout << num;
	SetConsoleTextAttribute(hConsole, console_default_color);
}

void cc_print(int color, int num){
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, color);
	cout << num;
	SetConsoleTextAttribute(hConsole, console_default_color);
}

