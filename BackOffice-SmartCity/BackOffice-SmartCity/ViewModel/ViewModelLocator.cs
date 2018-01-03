using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Practices.ServiceLocation;
using GalaSoft.MvvmLight.Ioc;
using GalaSoft.MvvmLight.Views;
using BackOffice_SmartCity.View;

namespace BackOffice_SmartCity.ViewModel
{
    public class ViewModelLocator
    {
        public ViewModelLocator()
        {
            ServiceLocator.SetLocatorProvider(() => SimpleIoc.Default);
            SimpleIoc.Default.Register<AcceuilViewModel>();
            SimpleIoc.Default.Register<ListResponsablesViewModel>();
            SimpleIoc.Default.Register<CreationResponsableViewModel>();
            SimpleIoc.Default.Register<CreationJardinViewModel>();
            SimpleIoc.Default.Register<ListJardinViewModel>();
            SimpleIoc.Default.Register<ModifierResponsableViewModel>();
            SimpleIoc.Default.Register<UtilisateurViewModel>();
            SimpleIoc.Default.Register<ModifierJardinViewModel>();
            SimpleIoc.Default.Register<ConnectionViewModel>();
            SimpleIoc.Default.Register<InfosAdminViewModel>();

            NavigationService navigation = new NavigationService();
            SimpleIoc.Default.Register<INavigationService>(() => navigation);
            navigation.Configure("Acceuil", typeof(Acceuil));
            navigation.Configure("CreationJardin", typeof(CreationJardin));
            navigation.Configure("CreationResponsable", typeof(CreationResponsable));
            navigation.Configure("ListJardin", typeof(ListJardin));
            navigation.Configure("ListResponsables", typeof(ListResponsables));
            navigation.Configure("ModifierJardin", typeof(ModifierJardin));
            navigation.Configure("ModifierResponsable", typeof(ModifierResponsable));
            navigation.Configure("Utilisateur", typeof(Utilisateur));
            navigation.Configure("Connection", typeof(Connection));
            navigation.Configure("InfosAdmin", typeof(InfosAdmin));
        }

        public AcceuilViewModel Acceuil
        {
            get
            {
                return ServiceLocator.Current.GetInstance<AcceuilViewModel>();
            }
        }       


        public CreationJardinViewModel CreationJardin
        {
            get
            {
                return ServiceLocator.Current.GetInstance<CreationJardinViewModel>();
            }
        }

      
        public CreationResponsableViewModel CreationResponsable
        {
            get
            {
                return ServiceLocator.Current.GetInstance<CreationResponsableViewModel>();
            }
        }

        public ListJardinViewModel ListJardin
        {
            get
            {
                return ServiceLocator.Current.GetInstance<ListJardinViewModel>();
            }
        }

        public ListResponsablesViewModel ListResponsables
        {
            get
            {
                return ServiceLocator.Current.GetInstance<ListResponsablesViewModel>();
            }
        }

        public ModifierJardinViewModel ModifierJardin
        {
            get
            {
                return ServiceLocator.Current.GetInstance<ModifierJardinViewModel>();
            }
        }

        public ModifierResponsableViewModel ModifierResponsable
        {
            get
            {
                return ServiceLocator.Current.GetInstance<ModifierResponsableViewModel>();
            }
        }

        public UtilisateurViewModel Utilisateur
        {
            get
            {
                return ServiceLocator.Current.GetInstance<UtilisateurViewModel>();
            }
        }

        public ConnectionViewModel Connection
        {
            get
            {
                return ServiceLocator.Current.GetInstance<ConnectionViewModel>();
            }
        }

        public InfosAdminViewModel InfosAdmin
        {
            get
            {
                return ServiceLocator.Current.GetInstance<InfosAdminViewModel>();
            }
        }

    }
}
