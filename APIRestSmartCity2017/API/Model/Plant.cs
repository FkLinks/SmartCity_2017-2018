using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class Plant
    {
        public decimal Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string UrlAudioGuide { get; set; }

        public ICollection<Plant_Garden> Plant_Garden { get; set; }
    }
}
