package fr.hotmail.danyvaise.vacreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Classe principale dans laquelle on manipule les objets.
 * C'est le coeur du programme.
 */

public class VaCreator implements Runnable
    {
    @Override
    public void run()
        {
        //Déclaration et initialisation variables
        UI ui = new UI();
        List folder_paths = new ArrayList();
        List filenames_no_clean = new ArrayList();
        List filesFullpath_no_clean = new ArrayList();
        File files_no_clean[] = null;
        File files[] = null;
        int nbPaths = 0;
        int nbFiles = 0;
        int nbFilePerFolder = 0;
        int nbFolders = 1;
        Random rand = new Random();
        Boolean atLeastOneFolder = false;
        
        //Affichage de l'entête du programme
        ui.displayHeader();
        
        //Saisie utilisateur du chemin
        //du répertoire à traiter
        ui.enterSourceFolders();
        folder_paths = ui.getPaths();
        nbPaths = folder_paths.size();
        
        //Clean des chemins pour éviter ceux qui sont incorrects
        FileFolderManager cleanPath = new FileFolderManager();
        for (int i=0; i<nbPaths; i++)
            {
            String currentPath = folder_paths.get(i).toString();
            
            if (cleanPath.isDirectory(currentPath) == false)
                {
                folder_paths.remove(i);
                nbPaths--;
                }
            else
                {
                atLeastOneFolder = true;
                }
            }
        
        //Saisie utilisateur du nombre
        //de fichiers par répertoire
        ui.enterNbFilePerFolder();
        nbFilePerFolder = ui.getNbFilePerFolder();
        
        //Saisie utilisateur du nombre
        //de fichiers par répertoire
        ui.enterNbFolders();
        nbFolders = ui.getNbFolders();       
        
        //Création de l'objet de gestion fichier/dossier
        //avec passage des répertoires à traiter
        FileFolderManager fm = new FileFolderManager(folder_paths);
        
        //Liste les fichier de/des répertoire(s) rentrés dans l'objet
        files_no_clean = fm.listFileFromFolders();
        
        //Traitement des instructions suivante
        //si un ou plusieurs fichiers existent
        if (files_no_clean != null)
            { 
            for (int i=0; i<files_no_clean.length; i++)
                {
                filenames_no_clean.add(files_no_clean[i].getName());
                filesFullpath_no_clean.add(files_no_clean[i].getAbsolutePath());
                }
            
            //On élimine les doublons pour ne pas avoir
            //plusieurs fois le même fichier
            int inBlackList = 0;
            nbFiles = files_no_clean.length;
            
            purgeWrongFiles(filenames_no_clean, filesFullpath_no_clean, nbFiles, inBlackList);
            
            nbFiles = filesFullpath_no_clean.size();
            files = new File[nbFiles];
            for (int i=0; i<filesFullpath_no_clean.size(); i++)
                {
                File currentFile = new File(filesFullpath_no_clean.get(i).toString());
                files[i] = currentFile;
                }
            }
        
        //Valeur par défaut du nombre de fichiers
        //par répertoire si valeur non remplie
        //ou égale à 0
        if (nbFilePerFolder == 0)
            {
            nbFilePerFolder = nbFiles;
            }
        
        //Calcul du nombre de répertoires à créer
        //si l'utilisateur choisi le mode
        //nombe de répertoires illimité
        if (nbFolders == 0 && nbFiles > 0)
            {
            nbFolders = nbFiles/nbFilePerFolder;
            
            //Si un reste de la division existe
            //on crée 1 répertorie en plus
            //pour les musiques restantes
            if (nbFiles%nbFilePerFolder > 0)
                {
                nbFolders += 1;
                }
            }
        
        //Calcul du nombre de fichiers à traiter
        //si l'utilisateur précise un nombre
        //de fichiers par répertoire ainsi
        //que le nombre de répertoires
        if (nbFolders > 0 && nbFilePerFolder > 0)
            {
            nbFiles = nbFolders * nbFilePerFolder;
            }
        
        if (atLeastOneFolder == true && nbFiles > 0)
            {
            Boolean use_blacklist_file = true;
            String blacklist_file_path = "./blacklist.txt";
            int nbBlackListFiles = 0;
            int nbFullBLackListSize = 0;
            List blacklist_files = new ArrayList();

            String targetFolder = "./VA-Randomized";

            String filename = "";
            String fullPath = "";

            int musicIndex = 0;

            //Saisie utilisateur du répertoire de destination
            ui.enterTargetFolderPath();

            if (!ui.getTargetFolderPath().equals(""))
                {
                targetFolder = ui.getTargetFolderPath();
                }

            targetFolder += '/';

            //Saisie utilisateur si
            //utilisation de la blacklist
            ui.useBlackListFile();
            use_blacklist_file = ui.getblacklistFile();

            if (use_blacklist_file)
                {
                //Création du fichier blacklist.txt
                try
                    {
                    fm.writeFile(blacklist_file_path, "");
                    }
                catch (FileNotFoundException ex)
                    {
                    }
                
                //Test de l'existence du fichier blacklist.txt
                if (fm.isFile(blacklist_file_path))
                    {
                    try
                        {
                        blacklist_files = fm.readFile(blacklist_file_path);
                        nbBlackListFiles = blacklist_files.size();
                        nbFullBLackListSize = nbFiles + nbBlackListFiles;
                        }
                    catch (FileNotFoundException ex)
                        {
                        }
                    }
                }

            //Création de la liste des fichiers random
            String randomizeList[][] = new String[nbFiles][nbFiles];

            //Création de la liste d'exclusion
            String blackList[];
            
            if (use_blacklist_file)
                {
                blackList = new String[nbFullBLackListSize];
            
                int index = 0;
                
                for (int i=nbFiles; i<nbFullBLackListSize; i++)
                    {
                    blackList[i] = blacklist_files.get(index).toString();
                    index++;
                    }
                }
            else
                {
                blackList = new String[nbFiles];
                }

            /*for (int i=0; i<blackList.length; i++)
                {
                System.out.println(blackList[i]);
                }*/
            
            //Partie calcul du randomize
            //Peuplement de la liste des fichiers à randomizer
            //Chaque nouveau fichier randomizé ne doit pas
            //figurer dans la black liste
            for (int i=0; i<nbFiles; i++)
                {
                int randomIndex = 0;

                if (i == 0 && use_blacklist_file == false)
                    {
                    //Retourne sur le total des fichiers un numéro de fichier aléatoire
                    randomIndex = rand.nextInt(nbFiles);

                    //Retourne le nom du fichier à la position de l'index
                    filename = files[randomIndex].getName();
                    fullPath = files[randomIndex].getAbsolutePath();

                    //Peuplement du tableau avec le nom du fichier à randomizer
                    randomizeList[i][i] = filename;
                    randomizeList[i][i+1] = fullPath;

                    //Ajout du fichier dans la black liste
                    blackList[i] = filename;
                    }
                else
                    {
                    //Parcours de la liste d'exclusion
                    //On randomize le fichier si ce dernier n'est pas matché
                    //Une fois randomizé le fichier est ajouté à la black liste
                    int inBlackList = 0;
                    randomIndex = rand.nextInt(nbFiles);
                    filename = files[randomIndex].getName();
                    fullPath = files[randomIndex].getAbsolutePath();
                    inBlackList = matchStringInTab(filename, blackList, false);

                    if (inBlackList > 0)
                        {
                        while (inBlackList > 0)
                            {
                            randomIndex = rand.nextInt(nbFiles);
                            filename = files[randomIndex].getName();
                            fullPath = files[randomIndex].getAbsolutePath();
                            inBlackList = matchStringInTab(filename, blackList, false);
                            }
                        randomizeList[i][0] = filename;
                        randomizeList[i][1] = fullPath;
                        blackList[i] = filename;
                        }
                    else
                        {
                        randomizeList[i][0] = filename;
                        randomizeList[i][1] = fullPath;
                        blackList[i] = filename;
                        }
                    }
                }

            //Partie copie des fichiers vers le targetFolder
            for (int i=1; i<nbFolders+1; i++)
                {
                String currentTargetFolder = targetFolder;

                if (nbFolders > 1)
                    {
                    //Création du répertoire CD(index) => CD1
                    currentTargetFolder = targetFolder + "CD" + i + '/';
                    }

                //Création du répertoire de destination
                fm.createFolder(currentTargetFolder);

                //Affichage du répertoire en cours de traitement
                ui.displayFolder(currentTargetFolder);

                for (int j=1; j<nbFilePerFolder+1; j++)
                    {
                    if (musicIndex < nbFiles)
                        {
                        filename = randomizeList[musicIndex][0];
                        fullPath = randomizeList[musicIndex][1];

                        Pattern pattern = Pattern.compile("^[0-9]*? - (.*)");
                        Matcher matcher = pattern.matcher(filename);

                        if (matcher.find())
                            {
                            filename = matcher.group(1);
                            }

                        //Déplace un fichier aléatoire dans le répertoire
                        //de l'index courant
                        fm.copyFile(fullPath, currentTargetFolder + j + " - " + filename);
                        }
                    else
                        {
                        break;
                        }
                    musicIndex ++;
                    }

                if (musicIndex == nbFiles)
                    {
                    break;
                    }
                }
            
            //Partie MAJ du fichier blaclist.txt si activée
            if (use_blacklist_file)
                {
                List filesToBlacklist = new ArrayList();
                
                for (int i=0; i<blackList.length; i++)
                    {
                    filesToBlacklist.add(blackList[i]);
                    }
                
                try
                    {
                    fm.writeFile(blacklist_file_path, filesToBlacklist);
                    }
                catch (FileNotFoundException ex)
                    {
                    }
                }
            }
        else
            {
            ui.displayError(1);
            }
        }
    
    public static void main(String[] args)
        {
        Thread T = new Thread(new VaCreator());
        
        //Appelle implicitement la méthode run()
        T.start();
        }
    
    public void purgeWrongFiles(List filenames_no_clean, List filesFullpath_no_clean, int nbFiles, int inBlackList)
        {
        int occurs = nbFiles;
        
        for (int i=0; i<nbFiles; i++)
            {
            String filename = filenames_no_clean.get(i).toString();
            inBlackList = matchStringInTab(filename, filenames_no_clean, true);

            Pattern pattern = Pattern.compile(".mp3|.flac|.wave|.wma|.m4a");

            //Vérification que le fichier est une extension musicale
            Boolean extensionCheck = pattern.matcher(filename.toLowerCase()).find();

            if (inBlackList > 1 || !extensionCheck)
                {
                filenames_no_clean.remove(i);
                filesFullpath_no_clean.remove(i);
                nbFiles--;
                }
            }
        
        occurs -= nbFiles;
        
        if (occurs > 0 )
            {
            purgeWrongFiles(filenames_no_clean, filesFullpath_no_clean, nbFiles, inBlackList);
            }
        }
    
    public int matchStringInTab(String str, String str_tab[], Boolean checkSeveralMatches)
        {
        int count = 0;
        
        for (int i=0; i<str_tab.length; i++)
            {
            if (str.equals(str_tab[i]))
                {
                count++;
                if (!checkSeveralMatches)
                    {
                    break;
                    }
                }
            }
        
        return count;
        }
    
    public int matchStringInTab(String str, List str_list, Boolean checkSeveralMatches)
        {
        int count = 0;
        
        for (int i=0; i<str_list.size(); i++)
            {
            if (str.equals(str_list.get(i).toString()))
                {
                count++;
                if (!checkSeveralMatches)
                    {
                    break;
                    }
                }
            }
        
        return count;
        }
    }
