using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Garden
    {
        public decimal NumGarden { get; set; }
        public string Name { get; set; }
        public decimal Superficie { get; set; }
        public string Street { get; set; }
        public decimal NumStreet { get; set; }
        public string Description { get; set; }
        public string GeographicalCoordinate { get; set; }
        //public string Picture { get; set; }
        public decimal? Note { get; set; }
    }
}
