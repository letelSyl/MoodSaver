# Correction

* Le dossier mipmap est destiné aux icon de lanceur uniquement. Il faut donc déplacer les images dans le dossier drawable.
* Sur les ImagesView il faut ajouter les attributs `android:contentDescription` pour des raisons d'ccessibilité pour les mals et non voyants.
* Les chaines de caracteres dans les layout doivent être placées dans le fichier `strings.xml`.
* Il est recommandé d'utilisé le système d'unité `sp` pour la propriété `textSize` .