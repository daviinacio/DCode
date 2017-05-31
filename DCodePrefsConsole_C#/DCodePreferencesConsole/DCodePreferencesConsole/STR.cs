using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class STR {
        // String ID
        public static int NonSelectedFileError = 0, InvalidCommand = 1, OpenWithoutCommand = 2, NeedEntryFileName = 3, FileNotExists = 4, FileClosed = 5,
            NeedCloseFile = 6, SaveFileBerofeClose = 7, PreferencesRequisits = 8, PreferencesFindRequisits = 9, PrefsNotFound = 10, PrefsExists = 11;
        // Lang ID
        public static int EN = 0, PT = 1;
        // Variables
        public static int lang = EN;
        // Help ID
        public static int HELP_MAIN = 0, HELP_FILE = 1;
        // Help
        private static String [][][] help = {
            // Main
            new String [][] {
                new String[] {"add", "Add a preference.", "Adiciona uma preferencia."},
                new String[] {"set", "Set the value of a preference.", "Modifica o valor da preferencia."},
                new String[] {"get", "Get the value of a preference.", "Retorna o valor da referencia."},
                new String[] {"remove", "Remove a reference.", "Remove uma referencia."},
                new String[] {"", "", ""},
                new String[] {"show", "Show all file preferences.", "Mostra todas as preferencias do arquivo."},
                new String[] {"file", "File options. 'file --help' to more.", "Opções do arquivo. 'file --help' para mais info.."},
                new String[] {"text", "Show the file content.", "Mostra o conteudo do arquivo."},
                new String[] {"", "", ""},
                new String[] {"dir", "Show all files and folders of diretory.", "Mostra todos os arquivos e pastas do diretorio."},
                new String[] {"cd", "Change the current diretory.", "Altera o local do diretorio atual."},
                new String[] {"", "", ""},
                new String[] {"clear", "Clear the console.", "Limpar o console."},
                new String[] {"close or exit", "Close console.", "Fechar o console."},
            },
            // File
            new String [][] {
                new String[] {"open", "Open a preference file.", "Abrir um arquivo de preferencia."},
                new String[] {"save", "Save the file.", "Salvar o arquivo."},
                new String[] {"close", "Close the file.", "Fechar o arquivo."},
                new String[] {"reload", "Reload the file.", "Recarregar o arquivo."}
            }
        };

        private static String[][] str = {
            // Error
            new String[]{
                "No file selected, enter \"file open (Local file without spaces)\"",
                "Nenhum arquivo selecionado, digite \"file open (Local do arquivo sem espaços)\""
            },
            new String[] {
                "Command not found",
                "Comando não encontrado"
            },
            // Open
            new String[]{ "You need entry other command", "Você precisa colocar outro comando" },
            new String[]{ "You need entry the file name without spaces" , "Você precisa colocar o nome do arquivo sem espaços"},
            new String[]{ "File not exists" , "O arquivo não existe"},
            new String[]{ "File closed" , "Arquivo fechado"},
            new String[]{ "You need close the file to use this command\n\n    file close" , "Você precisa fechar o arquivo pra usar esse comando\n\n file close"},
            new String[]{ "Exists a opened file, do you wanna save?" , "Existe um aqruivo aberto, deseja salvar-lo?"},
            new String[]{ "You need entry the key and value:\n\n    <key> <value>" , "Você precisa colocar o key e o value:\n\n    <key> <value>"},
            new String[]{ "You need entry the key and ifNotFound:\n\n    <key> <ifNotFound>" , "Você precisa colocar o key e o ifNotFound:\n\n    <key> <ifNotFound>"},
            new String[]{ "Preference not found:\n\n    add <key> <value>" , "Preferencia não encontrada:\n\n   add <key> <value>"},
            new String[]{ "This preference exists" , "Essa preferencia já existe"}
        };

        // Methods
        public static String get(int id) {
            return str[id][lang];
        }

        public static void showHelp(int local) {
            int width = 16;
            String[][] localHelp = help[local];
            for (int i = 0; i < localHelp.Length; i++) {
                String[] h = localHelp[i];
                Console.WriteLine("    " + Program.tabulacao(h[0], width) + h[lang + 1]);
            }
        }
    }
}
