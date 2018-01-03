using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Garden
    {

        public Garden()
        {
            PointOfInterest = new HashSet<PointOfInterest>();
            GuidedTour = new HashSet<GuidedTour>();
        }

        public decimal NumGarden { get; set; }
        public string Name { get; set; }
        public decimal Superficie { get; set; }
        public string Street { get; set; }
        public decimal NumStreet { get; set; }
        public string Description { get; set; }
        public string GeographicalCoordinate { get; set; }
        public string UrlImg { get; set; }
        public string UrlAudio { get; set; }
        public decimal? Note { get; set; }

        public ICollection<PointOfInterest> PointOfInterest { get; set; }
        public ICollection<GuidedTour> GuidedTour { get; set; }
        public ICollection<Event_Garden> Event_Garden { get; set; }
        public ICollection<Plant_Garden> Plant_Garden { get; set; }
        public ICollection<Responsible_Garden> Responsible_Garden { get; set; }
    }
}
