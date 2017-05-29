using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class Program {
        public static int EN = 0, PTBR = 1;
        public static int lang = EN;

        private static DCode dcode = new DCode('.', ' ', ';');
        private static DCodePreferences dPrefs;

        static void Main(string[] args) {
            clearConsole();

            while (true) {
                Console.Write("> ");
                String[] commands = dcode.unCode(dcode.getOpen() + Console.ReadLine() + dcode.getSpace());

                switch (commands[0]) {
                    case "add": add(commands); break;
                    case "set": set(commands); break;
                    case "remove": remove(commands); break;

                    case "file": file(commands); break;

                    case "clear": clearConsole(); break;

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
