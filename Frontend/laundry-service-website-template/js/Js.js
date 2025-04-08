document.addEventListener('DOMContentLoaded', function() {
    // Get all navigation links
    const navLinks = document.querySelectorAll('.nav-links a');
    
    // Function to set active navigation link
    function setActiveNavLink() {
        // Get current scroll position
        const scrollPosition = window.scrollY;

        // Check each section to determine which one is in view
        document.querySelectorAll('section, div.hero, div.container, div.footer').forEach(section => {
            if (!section.id) return;
            
            const sectionTop = section.offsetTop - 100;
            const sectionHeight = section.offsetHeight;
            
            if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
                // Remove active class from all links
                navLinks.forEach(link => link.classList.remove('active'));
                
                // Add active class to the corresponding link
                const activeLink = document.querySelector(`.nav-links a[href="#${section.id}"]`);
                if (activeLink) {
                    activeLink.classList.add('active');
                }
            }
        });
        
        // Handle special case for the top of the page
        if (scrollPosition < 100) {
            navLinks.forEach(link => link.classList.remove('active'));
            const homeLink = document.querySelector('.nav-links a[href="degree.html"]');
            if (homeLink) {
                homeLink.classList.add('active');
            }
        }
    }
    
    // Set active link on page load
    setActiveNavLink();
    
    // Set active link on scroll
    window.addEventListener('scroll', setActiveNavLink);
    
    // Add click event to navigation links for smooth scrolling
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            // Only handle internal links
            const targetId = this.getAttribute('href');
            if (targetId.startsWith('#')) {
                e.preventDefault();
                
                const targetElement = document.getElementById(targetId.substring(1));
                if (targetElement) {
                    window.scrollTo({
                        top: targetElement.offsetTop - 80,
                        behavior: 'smooth'
                    });
                    
                    // Update URL hash without scrolling
                    history.pushState(null, null, targetId);
                    
                    // Update active link
                    navLinks.forEach(navLink => navLink.classList.remove('active'));
                    this.classList.add('active');
                }
            }
        });
    });
});