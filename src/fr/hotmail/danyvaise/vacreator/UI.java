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
    
    public UI()
        {
        sc_input = new Scanner(System.in);
        targetFolderPath = "";
        nbFiles = 0;
        nbFolders = 0;
        }
    
    public UI(List tab_paths, String str_targetFolderPath, int i_nbFiles, int i_nbFolders)
        {
        sc_input = new Scanner(System.in);
        paths = tab_paths;
        targetFolderPath = str_targetFolderPath;
        nbFiles = i_nbFiles;
        nbFolders = i_nbFolders;
        }
    
    public void displayHeader()
        {
        System.out.println("=============================");
        System.out.println("VACreator 0.3.0");
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
            System.out.println("Please enter the music directory : ");
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
        
        while (nbFiles == 0)
            {
            System.out.println("Please enter a number of files by directory : ");
            s = sc_input.nextLine();
            
            if (s.equals(""))
                {
                s = "0";
                }
            
            nbFiles = Integer.parseInt(s);
            }
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
        System.out.println("Please enter the target directory : ");
        s = sc_input.nextLine();
        targetFolderPath = s;
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
            System.err.println("\nNo files in the directory !");
            }
        
        if (i == 2)
            {
            System.err.println("\nThe directory is incorrect !");
            }
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
    }
