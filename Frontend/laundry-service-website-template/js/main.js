  // Tab functionality
  document.querySelectorAll('.tab-button').forEach(button => {
    button.addEventListener('click', () => {
        // Remove active class from all tabs
        document.querySelectorAll('.tab-button').forEach(btn => {
            btn.classList.remove('active');
        });
        document.querySelectorAll('.tab-content').forEach(content => {
            content.classList.remove('active');
        });
        
        // Add active class to clicked tab
        button.classList.add('active');
        document.getElementById(button.dataset.tab).classList.add('active');
    });
});

// Form submission
document.getElementById('degree-form').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Here you would process the form data
    alert('¡Grado académico agregado con éxito!');
    this.reset();
});