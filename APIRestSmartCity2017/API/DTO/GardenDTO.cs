using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIRestSmartCity2017.DTO
{
    public class GardenDTO
    {
        public decimal NumGarden { get; set; }
        public string Name { get; set; }
        public decimal Superficie { get; set; }
        public string Street { get; set; }
        public decimal NumStreet { get; set; }
        public string Description { get; set; }
        public string GeographicalCoordinate { get; set; }
        public string UrlImg { get; set; }
        public string urlAudio { get; set; }
        public decimal? Note { get; set; }
    }
}
