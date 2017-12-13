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
        public decimal NumGarden { get; set; }
        public string Name { get; set; }
        public decimal Superficie { get; set; }
        public string Street { get; set; }
        public decimal NumStreet { get; set; }
        public string Description { get; set; }
        public string GeographicalCoordinate { get; set; }        
        public decimal? Note { get; set; }
        public string Picture { get; set; }
    }
}
