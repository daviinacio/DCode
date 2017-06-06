#include <iostream>
#include <string>

#include "resources.h"

using namespace std;

void c_clear(){
	system("cls");
}

void c_cabecalho(int color){
	c_clear();
	cc_print(color, " DCode Preferences Console C++ \n");
	cc_print(color, "  copyright (c) DaviApps 2017  \n");
	cc_print(color, "-------------------------------\n");
}

void c_cabecalho(){
	c_cabecalho(color_lightGray);
}

string c_getLine(){
	string in;
	getline(cin, in);
	return in;
}

string c_getLine(string out){
	cout << endl << out;
	return c_getLine();
}

string c_getLine(int str_id, string out){
	return c_getLine(out + R.getString(str_id));
}

bool c_cinEnpty(){
	return ((int) std::cin.peek()) == 10;
}

string c_get(){
	string in;
	cin >> in;
	return in;
}

string c_get(string out){
	cout << endl << out;
	return c_get();
}

string c_get(int str_id){
	return c_get(R.getString(str_id));
}

string c_get(int str_id, string out){
	return c_get(out + R.getString(str_id));
}

string cc_get(int color, string out){
	//cout << endl;
	cc_print(color, out);
	return c_get();
}

string cc_get(int color, int str_id){
	return cc_get(color, R.getString(str_id));
}

string cc_get(int color, int str_id, string out){
	return cc_get(color, out + R.getString(str_id));
}


