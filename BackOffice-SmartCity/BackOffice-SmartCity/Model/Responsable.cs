using BackOffice_SmartCity.Service;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Popups;

namespace BackOffice_SmartCity.Model
{
    public class Responsable
    {
        private decimal registrationNumber;
        private string login;
        private string password;
        private string sex;
        private decimal? age;
        private string phoneNumber;

        #region Propriétés
        public decimal RegistrationNumber
        {
            get
            {
                return registrationNumber;
            }
            set
            {
                if (value > 0 && value is decimal)
                {
                    registrationNumber = value;
                }
            }
        }

        public string Login
        {
            get
            {
                return login;
            }
            set
            {
                if(value is String && value != "")
                {
                    login = value;
                }
            }
        }
        public string Password
        {
            get
            {
                return password;
            }
            set
            {
                password = value;
            }
        }
        public decimal? Age
        {
            get
            {
                return age;
            }
            set
            {
                if(value is Decimal && value > 18)
                {
                    age = value;
                }                
            }
        }
        public string Sex
        {
            get
            {
                return sex;
            }
            set
            {
                if (value is String && (value.Equals("M") || value.Equals("F")))
                {
                    sex = value;
                }
            }
        }
        public string PhoneNumber
        {
            get
            {
                return phoneNumber;
            }
            set
            {               
                phoneNumber = value;                                
            }
        }

        #endregion
    }
}
