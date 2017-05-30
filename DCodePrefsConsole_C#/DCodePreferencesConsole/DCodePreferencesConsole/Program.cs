using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {

    class Program {

        private static DCode dcode = new DCode('.', ' ', ';');
        private static DCodePreferences dPrefs;
        private static String diretory = "C:/";

        public static bool running = true;

        static void Main(string[] args) {
            clearConsole();

            STR.lang = STR.EN;

            while (running) {
                Console.Write("> ");
                String[] commands = dcode.unCode(dcode.getOpen() + Console.ReadLine() + dcode.getSpace());

                switch (commands[0]) {
                    case "add": add(commands); break;
                    case "set": set(commands); break;
                    case "remove": remove(commands); break;

                    case "file": file(commands); break;

                    case "dir": dir(); break;
                    case "cd": cd(commands); break;

                    case "clear": clearConsole(); break;

                    case "close": running = false; break;
                    case "exit": running = false; break;

                    default: Error.InvalidCommand(); break;
                };
            }
        }

        private static void add(String[] cmd) {
            if (dPrefs == null) Error.NonSelectedFileError();
        }

        private static void set(String[] cmd) {
            if (dPrefs == null) Error.NonSelectedFileError();
        }

        private static void remove(String[] cmd) {
            if (dPrefs == null) Error.NonSelectedFileError();
        }

        private static void file(String[] cmd) {
            if (cmd.Length <= 1)
                Console.WriteLine(STR.get(STR.OpenWithoutCommand));
            else if (cmd[1] == "open") {
                if (cmd.Length <= 2)
                    Console.WriteLine(STR.get(STR.NeedEntryFileName));
                else
                    Console.WriteLine("Open file '" + cmd[2] + "'");
            } else if (cmd[1] == "save")
                Console.WriteLine("Save file");
            else if (cmd[1] == "close")
                Console.WriteLine("Close file");
            else
                Error.InvalidCommand();
        }

        private static void dir() {
            Console.WriteLine("Diretory: \"" + diretory + "\"");
            String[] folders = new String[] { "Folder1", "Folder2" };

            for (int i = 0; i < folders.Length; i++) {
                Console.WriteLine(" " + folders[i]);
            }
        }

        private static void cd(String[] cmd) {
            if (cmd.Length <= 1)
                Console.WriteLine("You need entry any folder name, after 'cd' command.");
        }

        // Console

        private static void clearConsole() {
            Console.Clear();
            Console.WriteLine(" DCode Preferences Console v1.0");
            Console.WriteLine("   copyright © DaviApps 2017  ");

            Console.WriteLine("--------------------------------");
            Console.WriteLine("");
        }
    }
}
