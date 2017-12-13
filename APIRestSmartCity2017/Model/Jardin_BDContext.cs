using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace APIRestSmartCity2017.Model
{
    public partial class Jardin_BDContext : Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityDbContext<ApplicationUser>
    {

        public Jardin_BDContext(DbContextOptions<Jardin_BDContext> options)
            :base(options)
        {
            
        }

        public virtual DbSet<ApplicationUser> ApplicationUser { get; set; }
        public virtual DbSet<Booking> Booking { get; set; }
        public virtual DbSet<Chatroom> Chatroom { get; set; }
        public virtual DbSet<Country> Country { get; set; }
        public virtual DbSet<Event> Event { get; set; }
        public virtual DbSet<Garden> Garden { get; set; }
        public virtual DbSet<GuidedTour> GuidedTour { get; set; }
        public virtual DbSet<Locality> Locality { get; set; }
        public virtual DbSet<Picture> Picture { get; set; }
        public virtual DbSet<Plant> Plant { get; set; }
        public virtual DbSet<PointOfInterest> PointOfInterest { get; set; }
        public virtual DbSet<Responsible> Responsible { get; set; }
        //public virtual DbSet<UserTable> UserTable { get; set; }

       

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Booking>(entity =>
            {
                entity.HasKey(e => new { e.UserBooking, e.GuidedTour });

                entity.Property(e => e.UserBooking)
                    .HasColumnName("userBooking")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.GuidedTour)
                    .HasColumnName("guidedTour")
                    .HasColumnType("numeric(8, 0)");

                entity.Property(e => e.BookingState)
                    .HasColumnName("bookingState")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.PayementState)
                    .HasColumnName("payementState")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.Price)
                    .HasColumnName("price")
                    .HasColumnType("numeric(6, 0)");

                entity.HasOne(d => d.GuidedTourNavigation)
                    .WithMany(p => p.Booking)
                    .HasForeignKey(d => d.GuidedTour)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("guidedTour_fk");

                entity.HasOne(d => d.UserBookingNavigation)
                    .WithMany(p => p.Booking)
                    .HasForeignKey(d => d.UserBooking)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("userBooking_fk");
            });

            modelBuilder.Entity<Chatroom>(entity =>
            {
                entity.HasKey(e => new { e.UserChat, e.Responsible });

                entity.Property(e => e.UserChat)
                    .HasColumnName("userChat")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.Responsible)
                    .HasColumnName("responsible")
                    .HasColumnType("numeric(11, 0)");

                entity.Property(e => e.SendDate)
                    .HasColumnName("sendDate")
                    .HasColumnType("date");

                entity.Property(e => e.Wording)
                    .IsRequired()
                    .HasColumnName("wording")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.HasOne(d => d.ResponsibleNavigation)
                    .WithMany(p => p.Chatroom)
                    .HasForeignKey(d => d.Responsible)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("responsible_fk");

                entity.HasOne(d => d.UserChatNavigation)
                    .WithMany(p => p.Chatroom)
                    .HasForeignKey(d => d.UserChat)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("userChat_fk");
            });

            modelBuilder.Entity<Country>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(3, 0)");

                entity.Property(e => e.NameCountry)
                    .IsRequired()
                    .HasColumnName("nameCountry")
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Event>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(5, 0)");

                entity.Property(e => e.Description)
                    .IsRequired()
                    .HasColumnName("description")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.EndTime)
                    .HasColumnName("endTime")
                    .HasColumnType("date");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.StartTime)
                    .HasColumnName("startTime")
                    .HasColumnType("date");

                entity.Property(e => e.Url)
                    .HasColumnName("url")
                    .HasMaxLength(100)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Garden>(entity =>
            {
                entity.HasKey(e => e.NumGarden);

                entity.Property(e => e.NumGarden)
                    .HasColumnName("numGarden")
                    .HasColumnType("numeric(2, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Comments)
                    .HasColumnName("comments")
                    .HasMaxLength(150)
                    .IsUnicode(false);

                entity.Property(e => e.Description)
                    .IsRequired()
                    .HasColumnName("description")
                    .HasMaxLength(500)
                    .IsUnicode(false);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.Note)
                    .HasColumnName("note")
                    .HasColumnType("numeric(3, 0)");

                entity.Property(e => e.NumStreet)
                    .HasColumnName("numStreet")
                    .HasColumnType("numeric(3, 0)");

                entity.Property(e => e.Street)
                    .IsRequired()
                    .HasColumnName("street")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Superficie)
                    .HasColumnName("superficie")
                    .HasColumnType("numeric(7, 0)");
            });

            modelBuilder.Entity<GuidedTour>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(8, 0)");

                entity.Property(e => e.EndTime)
                    .HasColumnName("endTime")
                    .HasColumnType("date");

                entity.Property(e => e.Price)
                    .HasColumnName("price")
                    .HasColumnType("numeric(6, 0)");

                entity.Property(e => e.StartTime)
                    .HasColumnName("startTime")
                    .HasColumnType("date");
            });

            modelBuilder.Entity<Locality>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(10, 0)");

                entity.Property(e => e.Country)
                    .HasColumnName("country")
                    .HasColumnType("numeric(3, 0)");

                entity.Property(e => e.NameLocality)
                    .IsRequired()
                    .HasColumnName("nameLocality")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.PostalCode)
                    .IsRequired()
                    .HasColumnName("postalCode")
                    .HasMaxLength(4)
                    .IsUnicode(false);

                entity.HasOne(d => d.CountryNavigation)
                    .WithMany(p => p.Locality)
                    .HasForeignKey(d => d.Country)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("country_fk");
            });

            modelBuilder.Entity<Picture>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(2, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Legend)
                    .IsRequired()
                    .HasColumnName("legend")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.Season)
                    .IsRequired()
                    .HasColumnName("season")
                    .HasMaxLength(9)
                    .IsUnicode(false);

                entity.Property(e => e.Url)
                    .IsRequired()
                    .HasColumnName("url")
                    .HasMaxLength(100)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Plant>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(6, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Description)
                    .IsRequired()
                    .HasColumnName("description")
                    .HasMaxLength(500)
                    .IsUnicode(false);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.UrlAudioGuide)
                    .HasColumnName("urlAudioGuide")
                    .HasMaxLength(100)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<PointOfInterest>(entity =>
            {
                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("numeric(3, 0)")
                    .ValueGeneratedOnAdd();

                entity.Property(e => e.Description)
                    .HasColumnName("description")
                    .HasMaxLength(500)
                    .IsUnicode(false);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Responsible>(entity =>
            {
                entity.HasKey(e => e.RegistrationNumber);

                entity.Property(e => e.RegistrationNumber)
                    .HasColumnName("registrationNumber")
                    .HasColumnType("numeric(11, 0)");

                entity.Property(e => e.Age)
                    .HasColumnName("age")
                    .HasColumnType("numeric(3, 0)");

                entity.Property(e => e.Login)
                    .HasColumnName("login")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasColumnName("password")
                    .HasMaxLength(30)
                    .IsUnicode(false);

                entity.Property(e => e.PhoneNumber)
                    .HasColumnName("phoneNumber")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.Sex)
                    .IsRequired()
                    .HasColumnName("sex")
                    .HasColumnType("char(1)");
            });           

            modelBuilder.Entity<ApplicationUser>(entity =>                         
            {
                entity.HasKey(e => e.UserName);

                entity.Property(e => e.Birthdate)
                    .HasColumnName("birthdate")
                    .HasColumnType("date");

                entity.Property(e => e.GeographicOrigins)
                    .HasColumnName("geographicOrigins")
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.PhoneNumber)
                    .HasColumnName("phoneNumber")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.Sex)
                    .IsRequired()
                    .HasColumnName("sex")
                    .HasMaxLength(1);
            });
        }
    }
}
