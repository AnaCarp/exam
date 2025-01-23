Să se realizeze o aplicație pentru un cabinet veterinar (urgențe), pentru servicii specifice.

Un client efectuează o solicitare pentru animalul de companie deținut, în care descrie problema și locul în care este necesară intervenția (adresa).

Un angajat alocat pentru situația respectivă va prelua solicitarea, stabilește dacă este o urgență și va iniția deplasarea către client.
În cazul în care se consideră că nu este urgență, clientul va fi informat, avînd posibilitatea să se deplaseze la clinică.

Clientul va fi notificat cu privire la stadiul solicitării: în analiză, acceptată (este urgență), respinsă (nu este urgență) și echipaj plecat spre client + timpul estimat de sosire (dacă solicitarea a fost acceptată).
Interfața aplicației permite adăugarea, vizualizarea, filtrarea/căutarea solicitărilor, precum și modificarea stării acestora.

Pentru implementare, utilizați o arhitectură Model View Presenter . Datele pot fi stocate într-o bază de date sau fișiere (text sau binare).
Asigurați accesul la date folosind modelul Active record.

Pot fi utilizate limbajele de programare Java sau C#, iar aplicația poate fi de tip desktop (cu interfață grafică sau consolă) sau Web.



Notă:

-Datele despre clienți, angajați și animalele de companie se preiau din fișiere/baza de date (nu trebuie preluate prin interfața aplicației)