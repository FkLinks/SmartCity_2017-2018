using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace BackOffice_SmartCity.Model
{
    public class Jardin
    {
        private decimal numGarden;
        private string name;
        private decimal? superficie;
        private string street;
        private decimal? numStreet;
        private string description;
        private string geographicalCoordinate;
        private string urlAudio;
        private string urlImg;
        private decimal? note;
        
        #region Propriétés
        public decimal NumGarden
        {
            get
            {
                return numGarden;
            }
            set
            {
                numGarden = value;
            }
        }
        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                if(value is String && value != "")
                {
                    name = value;
                }
            }
        }
        public decimal? Superficie
        {
            get
            {
                return superficie;
            }
            set
            {
                if(value>0 && value is decimal)
                {
                    superficie = value;
                }
            }
        }
        public string Street
        {
            get
            {
                return street;
            }
            set
            {
                if(value is String && value != "")
                {
                    street = value;
                }
            }
        }
        public decimal? NumStreet
        {
            get
            {
                return numStreet;
            }
            set
            {
                if(value > 0 && value is Decimal)
                {
                    numStreet = value;
                }
            }
        }
        public string Description
        {
            get
            {
                return description;
            }
            set
            {
                if(value is String && value != "")
                {
                    description = value;
                }
            }
        }
        public string GeographicalCoordinate
        {
            get
            {
                return geographicalCoordinate;
            }
            set
            {
                if(value is String && value != "")
                {
                    geographicalCoordinate = value;
                }
            }
        }        
        public string UrlAudio
        {
            get
            {
                return urlAudio;
            }
            set
            {
                if(value is String)
                {
                    urlAudio = value;
                }
            }
        }

        public string UrlImg
        {
            get
            {
                return urlImg;
            }
            set
            {
                if (value is String)
                {
                    urlImg = value;
                }
            }
        }

        public decimal? Note
        {
            get
            {
                return note;
            }
            set
            {
                if(value is Decimal && (value >= 0 && value < 6))
                {
                    note = value;
                }
            }
        }
        #endregion
    }
}
