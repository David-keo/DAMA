    package com.example.tabs

    import android.content.Intent
    import android.net.Uri
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.TextView
    import androidx.fragment.app.Fragment

    class CarreraFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container:
        ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_carrera, container, false)
            view.findViewById<TextView>(R.id.tvCarrera).text = "Ingeniería en desarrollo de software"
            view.findViewById<TextView>(R.id.tvCiclos).text = "9 ciclos"
            view.findViewById<TextView>(R.id.tvMaterias).text = "Desarrollo de aplicaciones móviles avanzadas"
            view.findViewById<Button>(R.id.btnMiUniversidad).setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.univo.edu.sv/"))
                startActivity(intent)
            }
            return view
        }

    }