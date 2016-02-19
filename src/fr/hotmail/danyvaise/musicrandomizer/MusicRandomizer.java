package fr.hotmail.danyvaise.musicrandomizer;

import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;

/*
 * Classe principale dans laquelle on manipule les objets.
 * C'est le coeur du programme.
 */

public class MusicRandomizer implements Runnable
    {
    @Override
    public void run()
        {
        //Déclaration et initialisation variables
        UI ui = new UI();
        String folder_path = "";
        File files[] = null;
        int nbFiles = 0;
        int nbFilePerFolder = 0;
        int nbFolders = 1;
        int folderMod = 0;
        Random rand = new Random();
        
        //Affichage de l'entête du programme
        ui.displayHeader();
        
        //Saisie utilisateur du chemin
        //du répertoire à traiter
        ui.enterPath();
        folder_path = ui.getPath();
        
        //Saisie utilisateur du nombre
        //de fichiers par répertoire
        ui.enterNbFilePerFolder();
        nbFilePerFolder = ui.getNbFilePerFolder();
        
        //Saisie utilisateur du nombre
        //de fichiers par répertoire
        ui.enterNbFolders();
        nbFolders = ui.getNbFolders();
        
        
        //Création de l'objet de gestion fichier/dossier
        //avec passage du répertoire à traiter
        FileFolderManager fm = new FileFolderManager(folder_path);
        
        //Liste les fichier du répertoire saisi
        files = fm.listFiles(folder_path);
        
        //Récupère le nombre de fichiers à traiter
        nbFiles = files.length;

        if (nbFolders == 0)
            {
            //Calcul du nombre de répertoires à créer
            nbFolders = nbFiles/nbFilePerFolder;
            
            /***
             Si un reste de la division existe
             on crée 1 répertorie en plus
             pour les musiques restantes
            */
            if (nbFiles%nbFilePerFolder > 0)
                {
                nbFolders += 1;
                }
            }
        else
            {
            folderMod = 1;
            }
       
        if (fm.getDirectory().isDirectory())
            {
            if (nbFiles == 0)
                {
                ui.displayError(1);
                }
            else
                {
                String targetFolder = folder_path + "/VA-Randomized";

                String filename = "";

                int musicIndex = 0;

                //Saisie utilisateur du répertoire de destination
                ui.enterTargetFolderPath();

                if (!ui.getTargetFolderPath().equals(""))
                    {
                    targetFolder = ui.getTargetFolderPath();
                    }

                targetFolder += '/';

                //Purge des fichier non musicaux
                //Déplacement dans le dossier corbeille
                for (int i=0; i<nbFiles; i++)
                    {
                    filename = files[i].getName().toLowerCase();

                    Pattern pattern = Pattern.compile(".mp3|.flac|.wave|.wma|.m4a");

                    //Vérification que le fichier est une extension musicale
                    Boolean extensionCheck = pattern.matcher(filename).find();

                    //Si le fichier n'a pas d'extension aurorisée
                    //on le déplace dans le dossier corbeille
                    if (extensionCheck == false)
                        {
                        String trashFolder = folder_path + "/_TRASH_/";
                        //Création du répertoire corbeille
                        fm.createFolder(trashFolder);
                        fm.moveFile(folder_path + '/' + filename, trashFolder + filename);
                        }

                    if (i == nbFiles-1)
                        {
                        files = fm.listFiles(folder_path);
                        nbFiles = files.length;
                        }
                    }

                //Création de la liste des fichiers random
                String randomizeList[] = new String[nbFiles];

                //Création de la liste d'exclusion
                String blackList[] = new String[nbFiles];

                /***
                 Partie calcul du randomize
                 Peuplement de la liste des fichiers à randomizer
                 Chaque nouveau fichier randomizé ne doit pas
                 figurer dans la black liste
                */
                for (int i=0; i<nbFiles; i++)
                    {
                    int randomIndex = 0;

                    if (i==0)
                        {
                        //Retourne sur le total des fichiers un numéro de fichier aléatoire
                        randomIndex = rand.nextInt(nbFiles);

                        //Retourne le nom du fichier à la position de l'index
                        filename = files[randomIndex].getName();

                        //Peuplement du tableau avec le nom du fichier à randomizer
                        randomizeList[i] = filename;

                        //Ajout du fichier dans la black liste
                        blackList[i] = filename;
                        }
                    else
                        {
                        //Parcours de la liste d'exclusion
                        //On randomize le fichier si ce dernier n'est pas matché
                        //Une fois randomizé le fichier est ajouté à la black liste
                        Boolean inBlackList = false;

                        randomIndex = rand.nextInt(nbFiles);
                        filename = files[randomIndex].getName();

                        inBlackList = checkStringInTab(filename, blackList);

                        if (inBlackList)
                            {
                            while (inBlackList)
                                {
                                randomIndex = rand.nextInt(nbFiles);
                                filename = files[randomIndex].getName();
                                inBlackList = checkStringInTab(filename, blackList);
                                }
                            randomizeList[i] = filename;
                            blackList[i] = filename;
                            }
                        else
                            {
                            randomizeList[i] = filename;
                            blackList[i] = filename;
                            }
                        }
                    }
                    
                
                /***
                 Partie copie des fichiers vers le targetFolder
                */
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
                    System.out.println(currentTargetFolder);

                    for (int j=1; j<nbFilePerFolder+1; j++)
                        {
                        if (musicIndex < nbFiles)
                            {
                            //Déplace un fichier aléatoire dans le répertoire
                            //de l'index courant
                            fm.copyFile(folder_path + '/' + randomizeList[musicIndex], currentTargetFolder + j + " - " + randomizeList[musicIndex]);
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
                }
            }
        else
            {
            ui.displayError(2);
            }
        }
    
    public static void main(String[] args)
        {
        Thread T = new Thread(new MusicRandomizer());
        
        //Appelle implicitement la méthode run()
        T.start();
        }
    
    public Boolean checkStringInTab(String str, String str_tab[])
        {
        Boolean b = false;
        
        for (int i=0; i<str_tab.length; i++)
            {
            if (str.equals(str_tab[i]))
                {
                b = true;
                break;
                }
            }
        
        return b;
        }
    }
