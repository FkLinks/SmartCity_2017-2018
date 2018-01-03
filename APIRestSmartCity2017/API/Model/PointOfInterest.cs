using System;
using System.Collections.Generic;

namespace APIRestSmartCity2017.Model
{
    public partial class PointOfInterest
    {
        public decimal Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public decimal Garden { get; set; }
        public Garden Fk_Garden { get; set; }
    }
}
