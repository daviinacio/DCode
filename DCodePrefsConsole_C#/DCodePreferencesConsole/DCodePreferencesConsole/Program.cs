using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {

    class Program {

        private static DCode dcode = new DCode('§', ' ', ';');
        private static DCodePreferences dPrefs;
        private static String diretory = "C:\\Users\\Davi\\Desktop";

        public static bool running = true;

        static void Main(string[] args) {
            newConsoleLoop(true);

            STR.lang = STR.EN;

            while (running) {
                String[] commands = dcode.unCode(dcode.getOpen() + Console.ReadLine() + dcode.getSpace());

                switch (commands[0]) {
                    case "add": add(commands); break;
                    case "set": set(commands); break;
                    case "remove": remove(commands); break;

                    case "show": show(commands); break;

                    case "file": file(commands); break;

                    case "dir": dir(); break;
                    case "cd": cd(commands); break;

                    case "clear": newConsoleLoop(true); continue;

                    case "help": STR.showHelp(); break;

                    case "close": running = false; break;
                    case "exit": running = false; break;

                    //case "t": Console.WriteLine(tabulacao(commands[1], 10) + "|"); break;

                    default: Error.InvalidCommand(); break;
                };
                newConsoleLoop(false);
            }
        }

        private static void add(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
        }

        private static void set(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
        }

        private static void remove(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
        }

        private static void show(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            int width = 15;
            //Console.WriteLine("Prefs: " + dPrefs.GetCount());
            //Console.WriteLine("(" + tabulacaoTitle("Key", width) + ")(" + tabulacaoTitle("Value", width * 2) + " )");
            for (int i = 0; i < dPrefs.GetCount(); i++) {
                DCodePrefItem prefsItem = dPrefs.GetByIndex(i);
                String key = prefsItem.getKey();
                String value = prefsItem.getValue();

                Console.WriteLine("[" + tabulacao(key, width) + "][" + tabulacao(value, (width * 2)) + "]");
            }
        }

        private static void file(String[] cmd) {
            if (cmd.Length <= 1)
                Console.WriteLine(STR.get(STR.OpenWithoutCommand));
            else if (cmd[1] == "open") {
                if (cmd.Length <= 2)
                    Console.WriteLine(STR.get(STR.NeedEntryFileName));
                else {
                    String fileName = diretory + "\\" + cmd[2];
                    if (File.Exists(fileName)) {
                        dPrefs = new DCodePreferences(new DCodeFile(fileName));
                        Console.WriteLine("Prefs: " + dPrefs.GetCount());
                    } else
                        Console.WriteLine(STR.get(STR.FileNotExists));
                }
            } else if (cmd[1] == "save") {
                if (dPrefs != null)
                    dPrefs.save();
                else
                    Error.NonSelectedFileError();
            } else if (cmd[1] == "close") {
                if (dPrefs != null) {
                    dPrefs = null;
                    Console.WriteLine(STR.get(STR.FileClosed));
                } else
                    Error.NonSelectedFileError();
            } else
                Error.InvalidCommand();
        }

        private static void dir() {
            String[] folders = new String[] { "Folder1", "Folder2" };

            for (int i = 0; i < folders.Length; i++) {
                Console.WriteLine(" " + folders[i]);
            }
        }

        private static void cd(String[] cmd) {
            if (cmd.Length <= 1)
                Console.WriteLine("You need entry any folder name, after 'cd' command.");
            else {
                if (dPrefs != null) { Console.WriteLine(STR.get(STR.NeedCloseFile)); return; }
            }
        }

        // Console

        private static void newConsoleLoop(bool clear) {
            if (clear) {
                Console.Clear();
                Console.WriteLine(" DCode Preferences Console v1.0");
                Console.WriteLine("   copyright © DaviApps 2017  ");

                Console.WriteLine("--------------------------------");
            }
            Console.WriteLine("");
            if (dPrefs != null)
                Console.Write(dPrefs.GetFile().getTitle());
            else
                Console.Write(diretory);
            Console.Write("> ");
        }

        // Gambiarras

        private static String tabulacaoTitle(String str, int width) {
            int len = (width - str.Length) / 2;
            String space = "";
            for (int i = 0; i < len; i++)
                space += " ";
            return space + str + space;
        }

        public static String tabulacao(String str, int width) {
            int len = (width - str.Length);
            //Console.WriteLine("l: " + len);
            for (int i = 0; i < len; i++)
                str += " ";
            return str;
        }
    }
}
