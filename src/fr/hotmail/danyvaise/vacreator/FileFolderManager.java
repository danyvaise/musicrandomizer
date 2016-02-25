package fr.hotmail.danyvaise.vacreator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Classe de gestion de fichier/dossier.
 * Permet de lire ou écrire des fichiers texte,
 * lister les noms de fichiers dans un répertoire
 * créer dossier,
 * effacer dossier et tout son contenu,
 * déplacer fichier dans un répertoire.
 */

public class FileFolderManager
    {
    List fileContent;
    String path;
    List paths = new ArrayList();
    
    public FileFolderManager()
        {
        fileContent = null;
        path = "";
        paths = null;
        }
    
    public FileFolderManager(String str_path)
        {
        fileContent = null;
        path = str_path;
        paths = null;
        }
    
    public FileFolderManager(List str_paths)
        {
        fileContent = null;
        path = "";
        paths = str_paths;
        }
    
    //Lit un fichier et met le contenu dans un tableau.
    //Alimente l'attribut file avec le contenu.
    public List readFile() throws FileNotFoundException
        {
        List l_file;
        Scanner sc_reader;
        
        l_file = new ArrayList();
        sc_reader = new Scanner(new FileReader(this.getPath()));
        
        //On alimente le tableau avec le contenu du fichier courant
        while (sc_reader.hasNextLine())
            {
            l_file.add(sc_reader.nextLine());
            }
        
        //Alimentation de l'attribut file
        //pour sauvegarder le contenu du fichier.
        fileContent = l_file;
        
        return l_file;
        }
    
    //Lit un fichier passé en paramètre et met le contenu dans un tableau.
    //Alimente l'attribut file avec le contenu.
    public List readFile(String str_path) throws FileNotFoundException
        {
        //Alimentation de l'attribut path
        //pour sauvegarder le chemin.
        path = str_path;
        
        List l_file;
        Scanner sc_reader;
        
        l_file = new ArrayList();
        sc_reader = new Scanner(new FileReader(str_path));
        
        //On alimente le tableau avec le contenu du fichier courant
        while (sc_reader.hasNextLine())
            {
            l_file.add(sc_reader.nextLine());
            }
        
        //Alimentation de l'attribut file
        //pour sauvegarder le contenu du fichier.
        fileContent = l_file;
        
        return l_file;
        }
    
    //Ecrit dans un fichier le contenu voulu à partir d'une chaine de caractères
    public void writeFile(String str_path, String str) throws FileNotFoundException
        {        
        //Alimentation de l'attribut path
        //pour sauvegarder le chemin.
        path = str_path;
        
        //Création d'un flux de sortie et création d'un fichier
        PrintWriter output = null;
        output = new PrintWriter(new FileOutputStream(path));
        
        //On append le fichier ouvert avec la chaine
        output.println(str);
                
        //Fermeture du flux de sortie
        output.close();
        }
    
    //Ecrit dans un fichier le contenu voulu à partir d'un objet List
    public void writeFile(String str_path, List l_data) throws FileNotFoundException
        {
        //Alimentation de l'attribut file
        //pour sauvegarder le contenu du fichier.
        fileContent = l_data;
        
        //Alimentation de l'attribut path
        //pour sauvegarder le chemin.
        path = str_path;
        
        //Création d'un flux de sortie et création d'un fichier
        PrintWriter output = null;
        output = new PrintWriter(new FileOutputStream(path));
        
        for (Object l_data1 : l_data)
            {
            //On append le fichier ouvert avec la ligne courante du tableau
            output.println(l_data1);
            }
        
        //Fermeture du flux de sortie
        output.close();
        }
    
    //Liste les fichiers du répertoire de l'objet
    //Retourne la liste dans un tableau
    public File[] listFiles()
        {
        File directory = new File(this.getPath());
        
        File f_files[] = directory.listFiles(new FileFilter()
            {
            @Override
            public boolean accept(File pathname)
                {
                return pathname.isFile();
                }
            });            
        
        return f_files;
        }
    
    //Liste les fichiers d'un répertoire
    //Retourne la liste dans un tableau
    public File[] listFiles(String str_path)
        {
        File directory = new File(str_path);
        
        File f_files[] = directory.listFiles(new FileFilter()
            {
            @Override
            public boolean accept(File pathname)
                {
                return pathname.isFile();
                }
            });
        
        //Alimentation de l'attribut path
        //pour sauvegarder le chemin.
        path = str_path;
        
        return f_files;
        }
    
    //Liste les fichiers de plusieurs répertoires
    //Retourne la liste dans un tableau
    public File[] listFiles(List tab_paths)
        {
        File f_files[] = null;
        List list = new ArrayList();
        
        System.out.println(tab_paths.size());
        
        for (int i=0; i<tab_paths.size(); i++)
            {
            File directory = new File(tab_paths.get(i).toString());
            
            File f_currentFiles[] = directory.listFiles(new FileFilter()
                {
                @Override
                public boolean accept(File pathname)
                    {
                    return pathname.isFile();
                    }
                });
            
            System.out.println(f_currentFiles[i].getName());
            
            list.add(f_currentFiles);
            
            //Alimentation de l'attribut paths
            //pour sauvegarder le chemin courant
            //dans le tableau des chemins.
            paths.add(tab_paths.get(i).toString());
            }
        
        list.toArray(f_files);
        
        return f_files;
        }
    
    //Créer un dossier
    public void createFolder(String str_path)
        {
        File dir = new File(str_path);
        dir.mkdirs();
        }
    
    //Effacer un dossier et tout son contenu
    public void deleteFolder()
        {
        File dir = new File(this.getPath());
        File[] f_files = listFiles(this.getPath());
        
        if (dir.isDirectory())
            {
            for (File f_file : f_files)
                {
                f_file.delete();
                }
            }
        else
            {
            System.err.println("\nThe directory is incorrect !");
            }
        
        dir.delete();
        }
    
    //Effacer un dossier passé en paramètre et tout son contenu
    public void deleteFolder(String str_path)
        {
        File dir = new File(str_path);
        File[] f_files = listFiles(str_path);
        
        if (dir.isDirectory())
            {
            for (File f_file : f_files)
                {
                f_file.delete();
                }
            }
        else
            {
            System.err.println("\nThe directory is incorrect !");
            }
        
        dir.delete();
        }
    
    //Déplacer fichier dans un répertoire / renommer
    public void moveFile(String str_sourceFile, String str_destFile)
        {
        File f_file = new File(str_sourceFile);
        File f_folder = new File(str_destFile);
        
        f_file.renameTo(f_folder);
        }
    
    //Copier fichier dans un répertoire
    public boolean copyFile(String str_sourceFile, String str_destFile)
        {
        File f_file = new File(str_sourceFile);
        File f_folder = new File(str_destFile);
        
	try
            {
            // Declaration et ouverture des flux
            java.io.FileInputStream sourceFile = new java.io.FileInputStream(f_file);

            try
                {
                java.io.FileOutputStream destinationFile = null;

                try
                    {
                    destinationFile = new FileOutputStream(f_folder);

                    // Lecture par segment de 0.5Mo 
                    byte buffer[] = new byte[512 * 1024];
                    int nbLecture;

                    while ((nbLecture = sourceFile.read(buffer)) != -1)
                        {
                        destinationFile.write(buffer, 0, nbLecture);
                        }
                    }
                finally
                    {
                    destinationFile.close();
                    }
                }
            finally
                {
                sourceFile.close();
                }
            }
        catch(IOException e)
            {
            e.printStackTrace();
            
            //Erreur
            return false;
            }
        
        //Résultat OK
	return true;  
        }
    
    //Check si l'objet est un fichier
    public Boolean isFile()
        {
        Boolean b = true;
        
        File f_file = new File(this.getPath());
        
        if (f_file.isFile() == false)
            {
            b = false;
            }
        
        return b;
        }
    
    //Check si l'objet du chemin passé est un fichier
    public Boolean isFile(String str_path)
        {
        Boolean b = true;
        
        File f_file = new File(str_path);
        
        if (f_file.isFile() == false)
            {
            b = false;
            }
        
        return b;
        }
    
    //Check si l'objet est un répertoire
    public Boolean isDirectory()
        {
        Boolean b = true;
        
        File f_file = new File(this.getPath());
        
        if (f_file.isDirectory() == false)
            {
            b = false;
            }
        
        return b;
        }
    
    //Check si l'objet du chemin passé est un répertoire
    public Boolean isDirectory(String str_path)
        {
        Boolean b = true;
        
        File f_file = new File(str_path);
        
        if (f_file.isDirectory() == false)
            {
            b = false;
            }
        
        return b;
        }
    
    public List getFileContent()
        {
        return fileContent;
        }
    
    public void setFileContent(List f_file)
        {
        fileContent = f_file;
        }
    
    public String getPath()
        {
        return path;
        }
    
    public void setPath(String str_path)
        {
        path = str_path;
        }
    
    public List getPaths()
        {
        return paths;
        }
    
    public void setPath(List tab_paths)
        {
        paths = tab_paths;
        }
    }