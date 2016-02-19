package fr.hotmail.danyvaise.musicrandomizer;

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
    List file;
    File[] filesInDirectory;
    File directory;
    String path;
    
    public FileFolderManager()
        {
        file = null;
        filesInDirectory = null;
        directory = null;
        path = "";
        }
    
    public FileFolderManager(List l_file)
        {
        file = l_file;
        filesInDirectory = null;
        directory = null;
        path = "";
        }
    
    public FileFolderManager(File[] l_files)
        {
        file = null;
        filesInDirectory = l_files;
        path = "";
        }
    
    public FileFolderManager(String str_path)
        {
        file = null;
        filesInDirectory = null;
        directory = null;
        path = str_path;
        }
    
    public FileFolderManager(List l_file, String str_path)
        {
        file = l_file;
        filesInDirectory = null;
        directory = null;
        path = str_path;
        }
    
    public FileFolderManager(File[] l_files, String str_path)
        {
        file = null;
        filesInDirectory = l_files;
        directory = null;
        path = str_path;
        }
    
    //Lit un fichier et met le contenu dans un tableau.
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
        file = l_file;
        
        return l_file;
        }
    
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
    
    //Ecrit dans un fichier le contenu voulu
    public void writeFile(String str_path, List l_data) throws FileNotFoundException
        {
        //Alimentation de l'attribut file
        //pour sauvegarder le contenu du fichier.
        file = l_data;
        
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
    
    public File[] listFiles(String str_path)
        {
        directory = new File(str_path);
        
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
        
        //Alimentation de l'attribut filesInDirectory
        //pour sauvegarder la liste des noms de fichier.
        filesInDirectory = f_files;
        
        return f_files;
        }
    
    //Créer un dossier
    public void createFolder(String str_path)
        {
        File dir = new File(str_path);
        dir.mkdirs();
        }
    
    //Effacer un dossier et tout son contenu
    public void deleteFolder(String str_path)
        {
        File dir = new File(str_path);
        File[] files = listFiles(str_path);
        
        if (dir.isDirectory())
            {
            for (File file : files)
                {
                file.delete();
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

    
    public List getFile()
        {
        return file;
        }
    
    public void setFile(List f_file)
        {
        file = f_file;
        }
    
    public String getPath()
        {
        return path;
        }
    
    public void setPath(String str_path)
        {
        path = str_path;
        }
    
    public File getDirectory()
        {
        return directory;
        }
    
    public void setDirectory(File f_directory)
        {
        directory = f_directory;
        }
    
    
    public File[] getFilesInDirectory()
        {
        return filesInDirectory;
        }
    
    public void setFilesInDirectory(File[] f_filesInDirectory)
        {
        filesInDirectory = f_filesInDirectory;
        }
    }
