package fr.hotmail.danyvaise.vacreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 * Classe d'interaction utilisateur.
 */

public class UI
    {
    Scanner sc_input;
    List paths = new ArrayList();
    String targetFolderPath;
    int nbFiles;
    int nbFolders;
    Boolean blacklist_file;
    
    public UI()
        {
        sc_input = new Scanner(System.in);
        targetFolderPath = "";
        nbFiles = 0;
        nbFolders = 0;
        blacklist_file = true;
        }
    
    public UI(List tab_paths, String str_targetFolderPath, int i_nbFiles, int i_nbFolders)
        {
        sc_input = new Scanner(System.in);
        paths = tab_paths;
        targetFolderPath = str_targetFolderPath;
        nbFiles = i_nbFiles;
        nbFolders = i_nbFolders;
        blacklist_file = true;
        }
    
    public void displayHeader()
        {
        System.out.println("=============================");
        System.out.println("VACreator 0.5.0");
        System.out.println("by @Dany Vaise");
        System.out.println("=============================\n");
        }
    
    public void enterSourceFolders()
        {
        int i = 0;
        String addFolder = "YES";
        Boolean empty = true;
            
        while (empty == true || addFolder.equals("YES"))
            {
            String str_tmp = "";
            System.out.println("Please enter the music directory :");
            str_tmp = sc_input.nextLine();
            empty = str_tmp.isEmpty();
            
            if (empty == false)
                {
                paths.add(str_tmp);
                System.out.println("Do you want to add another folder ? YES/NO");
                addFolder = sc_input.nextLine().toUpperCase();
                
                if (addFolder.equals("YES"))
                    {
                    i++;
                    }
                else
                    {
                    if (addFolder.equals("NO"))
                        {
                        break;
                        }
                    }
                }
            }
        }
    
    public void enterNbFilePerFolder()
        {
        String s = "";
        System.out.println("Please enter a number of files by directory.\n0 or nothing for unlimited files :");
        s = sc_input.nextLine();
        
        if (s.equals(""))
            {
            s = "0";
            }
        
        nbFiles = Integer.parseInt(s);
        }
    
    public void enterNbFolders()
        {
        String s = "";
        System.out.println("Please enter a number of folder.\n0 or nothing for unlimited folders :");
        s = sc_input.nextLine();
        
        if (s.equals(""))
            {
            s = "0";
            }
        
        nbFolders = Integer.parseInt(s);
        }
    
    public void enterTargetFolderPath()
        {
        String s = "";
        System.out.println("Please enter the target directory :");
        s = sc_input.nextLine();
        targetFolderPath = s;
        }
    
    public void useBlackListFile()
        {
        Boolean b = true;
        String s = "";
        System.out.println("Would you use the blacklist ? YES/NO (ENTER = YES)");
        s = sc_input.nextLine();
        
        if (s.equals(""))
            {
            s = "YES";
            }
        else
            {
            s = s.toUpperCase();
            }
        
        if (!(s.equals("YES")))
            {
            b = false;
            }
        
        blacklist_file = b;
        }
    
    public void displayFileName(String fileName)
        {
        System.out.print(fileName + "\n");
        }
    
    public void displaySeparator(int i)
        {
        if (i == 1)
            {
            System.out.println();
            System.out.println("-------------------------------");
            }
        
        if (i == 2)
            {
            System.out.println("-------------------------------");
            }
        }
    
    public void displayError(int i)
        {
        if (i == 1)
            {
            System.err.println("\nNo music found !");
            }
        }
    
    public void displayFolder(String str_folder)
        {
        System.out.println(str_folder);
        }
    
    public List getPaths()
        {
        return paths;
        }
    
    public void setPaths(List tab_paths)
        {
        paths = tab_paths;
        }
    
    public String getTargetFolderPath()
        {
        return targetFolderPath;
        }
    
    public void setTargetFolderPath(String str_targetFolderPath)
        {
        targetFolderPath = str_targetFolderPath;
        }
    
    public int getNbFilePerFolder()
        {
        return nbFiles;
        }
    
    public void setNbFilePerFolder(int i_nbFiles)
        {
        nbFiles = i_nbFiles;
        }
    
    public int getNbFolders()
        {
        return nbFolders;
        }
    
    public void setNbFolder(int i_nbFolders)
        {
        nbFolders = i_nbFolders;
        }
    
    public Boolean getblacklistFile()
        {
        return blacklist_file;
        }
    
    public void setBlackListFile(Boolean b_blacklist_file)
        {
        blacklist_file = b_blacklist_file;
        }
    }
