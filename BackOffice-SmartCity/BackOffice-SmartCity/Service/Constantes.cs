using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Service
{
    public class Constantes
    {
        public const String MESSAGE_ERREUR = "Attention ! Vous avez probablement commis des erreurs dans l'encodage du formulaire, veuillez revérifier";
        public const String TITRE_ERREUR = "Erreur Encodage";
        public const String MESSAGE_SUPPRESSION = "Voulez-vous vraiment supprimer ";
        public const String TITRE_SUPPRESSION = "Suppression";
        public const String BAD_CONNECTION = "Le nom d'utilisateur ou le mot de passe est incorrect";
        public const String TITRE_ERREUR_CONNECTION = "Erreur Connexion";
        public const String BEARER = "Bearer";
        public const String TITRE_MODIF_OK = "Modification enregistrée";
        public const String TITRE_ERREUR_MODIF = "Modification Erronée";
        public const String MESSAGE_ERREUR_MODIF = "Attention ! Un problème est survenu sur le serveur";
        public const String MESSAGE_OK_MODIF = "Votre modification a bien été enregistrée, vous allez maintenant être redirigé à l'accueil";
        public const String MESSAGE_ERREUR_CONNEXION = "Attention ! Un problème est survenu sur le serveur, veuillez vérifier votre connexion";
        public const String TITRE_ERREUR_CONNEXION = "Perte de connexion";
        public const String TITRE_ERREUR_DEL_ADMIN = "Suppression d'un admin";
        public const String MESSAGE_ERREUR_DEL_ADMIN = "Attention ! Vous ne pouvez supprimer un Administateur du système ! ";

        public const String API_RESPONSIBLES = "http://smartcity-jardin-20172018.azurewebsites.net/api/Responsibles/";
        public const String API_GARDENS = "http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens/";
        public const String API_ACCOUNT = "http://smartcity-jardin-20172018.azurewebsites.net/api/Account/";
        public const String API_ADMIN = "http://smartcity-jardin-20172018.azurewebsites.net/api/Account/AdminUWP";
        public const String API_JWT = "http://smartcity-jardin-20172018.azurewebsites.net/api/Jwt";
        public const String API_COUNTUSERS = "http://smartcity-jardin-20172018.azurewebsites.net/api/Account/Count/Users";
        public const String API_COUNTGARDENS = "http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens/Count/Gardens";
        public const String API_COUNTRESPON = "http://smartcity-jardin-20172018.azurewebsites.net/api/Responsibles/Count/Responsibles";
        public const String API_ROLES = "http://smartcity-jardin-20172018.azurewebsites.net/api/Account/Roles";

        public static String[] Sex = { "M", "F" };

        private static String TOKEN_ADMIN = null;
        public static String TOKEN_ADMIN_PROP
        {
            get
            {
                return TOKEN_ADMIN;
            }
            set
            {
                TOKEN_ADMIN = value;
            }
        }

        public static HttpClient GetHttp()
        {
            return new HttpClient();
        }
    }
}
