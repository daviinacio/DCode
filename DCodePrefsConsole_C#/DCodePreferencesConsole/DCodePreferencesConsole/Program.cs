using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
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
                    case "get": get(commands); break;
                    case "remove": remove(commands); break;

                    //case "show": show(); break;

                    case "file": file(commands); break;

                    //case "text": if (dPrefs != null) Console.WriteLine(dPrefs.GetFile().getText()); else Console.WriteLine(STR.get(STR.NonSelectedFileError)); break;

                    case "dir": dir(); break;
                    case "cd": cd(commands); break;

                    case "clear": newConsoleLoop(true); continue;

                    case "help": STR.showHelp(STR.HELP_MAIN); break;

                    case "close": running = false; break;
                    case "exit": running = false; break;

                    case "t": dPrefs = new DCodePreferences(new DCodeFile(diretory + "/dprefs.dcode")); break;

                    case "": break;

                    default: Error.InvalidCommand(); break;
                };
                newConsoleLoop(false);
            }
        }

        private static void add(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            if (cmd.Length <= 2) { Console.WriteLine(STR.get(STR.PreferencesRequisits)); return; }
            if (!dPrefs.Contains(cmd[1]))
                dPrefs.Add(cmd[1], cmd[2]);
            else
                Console.WriteLine(STR.get(STR.PrefsExists));
        }

        private static void set(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            if (cmd.Length <= 2) { Console.WriteLine(STR.get(STR.PreferencesRequisits)); return; }
            if (dPrefs.Contains(cmd[1]))
                dPrefs.Set(cmd[1], cmd[2]);
            else
                Console.WriteLine(STR.get(STR.PrefsNotFound));

        }

        private static void get(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            if (cmd.Length == 2) {
                if (cmd[1] == "*" || cmd[1] == "all") { show(); return; }
                Console.WriteLine(dPrefs.Get(cmd[1], STR.get(STR.PrefsNotFound)));
            } else if (cmd.Length == 3) {
                Console.WriteLine(dPrefs.Get(cmd[1], cmd[2]));
            } else
                Console.WriteLine(STR.get(STR.PreferencesFindRequisits));
        }

        private static void remove(String[] cmd) {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            if (cmd.Length <= 1) { Console.WriteLine(STR.get(STR.InvalidCommand)); return; }
            if (cmd[1] == "*" || cmd[1] == "all") {
                int len = dPrefs.GetCount();
                for (int i = 0; i < len; i++)
                    dPrefs.Remove(dPrefs.GetByIndex(0).getKey());
            } else if (dPrefs.Contains(cmd[1])) {
                dPrefs.Remove(cmd[1]);
            } else
                Console.WriteLine(STR.get(STR.PrefsNotFound));
        }

        private static void show() {
            if (dPrefs == null) { Error.NonSelectedFileError(); return; }
            int width = 25;
            if (dPrefs.GetCount() > 0)
                for (int i = 0; i < dPrefs.GetCount(); i++) {
                    DCodePrefItem prefsItem = dPrefs.GetByIndex(i);
                    String key = prefsItem.getKey();
                    String value = prefsItem.getValue();

                    Console.WriteLine("[" + i + "] " + tabulacao(key, width) + "" + tabulacao(value, (width * 2)) + "");
                } else
                Console.WriteLine("Preferences not found");
        }

        private static void file(String[] cmd) {
            if (cmd.Length <= 1)
                Console.WriteLine(STR.get(STR.OpenWithoutCommand));
            else if (cmd[1] == "create") {
                if (cmd.Length <= 2)
                    Console.WriteLine(STR.get(STR.NeedEntryFileName));
                else {
                    String fileName = diretory + "\\" + cmd[2];
                    if (File.Exists(fileName)) { Console.WriteLine(STR.get(STR.FileExists)); return; }

                    if (dPrefs != null) {
                        Console.Write(STR.get(STR.SaveFileBerofeClose) + " (y/n)> ");
                        if (Console.Read() == 'y')
                            dPrefs.save();
                        dPrefs = null;
                        Console.WriteLine(STR.get(STR.FileClosed));
                    }

                    dPrefs = new DCodePreferences(new DCodeFile(fileName));
                    dPrefs = new DCodePreferences(new DCodeFile(fileName));
                }
            } else if (cmd[1] == "open") {
                if (cmd.Length <= 2)
                    Console.WriteLine(STR.get(STR.NeedEntryFileName));
                else {
                    String fileName = diretory + "\\" + cmd[2];
                    if (!File.Exists(fileName)) { Console.WriteLine(STR.get(STR.FileNotExists)); return; }

                    if (dPrefs != null) {
                        Console.Write(STR.get(STR.SaveFileBerofeClose) + " (y/n)> ");
                        if (Console.Read() == 'y')
                            dPrefs.save();
                        dPrefs = null;
                        Console.WriteLine(STR.get(STR.FileClosed));
                    }

                    dPrefs = new DCodePreferences(new DCodeFile(fileName));

                    if (dPrefs.GetFile().getStatusKey() != DCodeFile.ALRIGHT) { dPrefs = null; return; }
                }
            } else if (cmd[1] == "delete") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                Console.Write(STR.get(STR.ConfirmDeleteFile) + " (y/n)> ");
                if (Console.Read() == 'y') {
                    dPrefs.GetFile().delete();
                    dPrefs = null;
                }
                Console.WriteLine(STR.get(STR.FileDeleted));
            } else if (cmd[1] == "save") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                dPrefs.save();
            } else if (cmd[1] == "close") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                dPrefs = null;
                Console.WriteLine(STR.get(STR.FileClosed));
            } else if (cmd[1] == "reload") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                String file = dPrefs.GetFile().getFile();
                dPrefs = new DCodePreferences(new DCodeFile(file));
                Console.WriteLine("Prefs: " + dPrefs.GetCount());
            } else if (cmd[1] == "dir") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                Console.WriteLine(dPrefs.GetFile().getFile());
            } else if (cmd[1] == "info") {
                if (dPrefs == null) { Error.NonSelectedFileError(); return; }
                DCodeFile dFile = dPrefs.GetFile();
                DCode df = new DCode(DCode.getMode(dFile.getFileText()));
                DCode dp = new DCode(DCode.getMode(dFile.getText()));

                //Console.WriteLine("File Properties");
                Console.WriteLine("Title: '" + dFile.getTitle() + "'");
                Console.WriteLine("Encode Type: '" + dFile.getEncodeType() + "'");
                Console.WriteLine("Encode Mode: '" + df.getOpen() + "', " + "'" + df.getSpace() + "', " + "'" + df.getClose() + "'");
                Console.WriteLine("Text: '" + dFile.getText() + "'");

                Console.WriteLine("");
                //Console.WriteLine("Content properties");
                Console.WriteLine("Content Encode Mode: '" + dp.getOpen() + "', " + "'" + dp.getSpace() + "', " + "'" + dp.getClose() + "'");

                // dir
            } else if (cmd[1] == "--help") {
                STR.showHelp(STR.HELP_FILE);
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
                Console.WriteLine(" DCode Preferences Console " + String.Format("v{0}", Assembly.GetExecutingAssembly().GetName().Version.ToString()));
                Console.WriteLine("     copyright © DaviApps 2017  ");

                Console.WriteLine("------------------------------------");
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
