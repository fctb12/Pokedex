package com.example.pokedex

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Switch
import android.widget.TextView

import androidx.appcompat.app.AppCompatDialogFragment

import java.util.ArrayList
import java.util.HashMap

class FilterDialog : AppCompatDialogFragment() {

    private var minAttack: EditText? = null
    private var minDefense: EditText? = null
    private var minHP: EditText? = null
    private var minAttackText: TextView? = null
    private var minDefenseText: TextView? = null
    private var minHPText: TextView? = null
    private var bug: Switch? = null
    private var dark: Switch? = null
    private var dragon: Switch? = null
    private var electric: Switch? = null
    private var fairy: Switch? = null
    private var fighting: Switch? = null
    private var fire: Switch? = null
    private var flying: Switch? = null
    private var ghost: Switch? = null
    private var grass: Switch? = null
    private var ground: Switch? = null
    private var ice: Switch? = null
    private var normal: Switch? = null
    private var poison: Switch? = null
    private var psychic: Switch? = null
    private var rock: Switch? = null
    private var steel: Switch? = null
    private var water: Switch? = null

    private val isSwitched = HashMap<String, Boolean>()
    private val minStats = ArrayList<String>()

    private var listener: FilterDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.filter_dialog, null)
        builder.setView(view)
                .setTitle("Filter")
                .setNegativeButton("Reset") { dialog, which -> listener!!.clear() }
                .setPositiveButton("Apply") { dialog, which ->
                    isSwitched["bug"] = bug!!.isChecked
                    isSwitched["dark"] = dark!!.isChecked
                    isSwitched["dragon"] = dragon!!.isChecked
                    isSwitched["electric"] = electric!!.isChecked
                    isSwitched["fairy"] = fairy!!.isChecked
                    isSwitched["fighting"] = fighting!!.isChecked
                    isSwitched["fire"] = fire!!.isChecked
                    isSwitched["flying"] = flying!!.isChecked
                    isSwitched["ghost"] = ghost!!.isChecked
                    isSwitched["grass"] = grass!!.isChecked
                    isSwitched["ground"] = ground!!.isChecked
                    isSwitched["ice"] = ice!!.isChecked
                    isSwitched["normal"] = normal!!.isChecked
                    isSwitched["poison"] = poison!!.isChecked
                    isSwitched["psychic"] = psychic!!.isChecked
                    isSwitched["rock"] = rock!!.isChecked
                    isSwitched["steel"] = steel!!.isChecked
                    isSwitched["water"] = water!!.isChecked

                    var inputMinAttack = "00"
                    var inputMinDefense = "00"
                    var inputMinHP = "00"

                    if (minAttack!!.text.toString() != "") {
                        inputMinAttack = minAttack!!.text.toString()
                    }
                    if (minDefense!!.text.toString() != "") {
                        inputMinDefense = minDefense!!.text.toString()
                    }
                    if (minHP!!.text.toString() != "") {
                        inputMinHP = minHP!!.text.toString()
                    }

                    minStats.add(inputMinAttack)
                    minStats.add(inputMinDefense)
                    minStats.add(inputMinHP)
                    listener!!.applyTypes(isSwitched, minStats)
                }

        minAttack = view.findViewById(R.id.minAttackInput)
        minDefense = view.findViewById(R.id.minDefenseInput)
        minHP = view.findViewById(R.id.minHPInput)
        minAttack!!.inputType = InputType.TYPE_CLASS_NUMBER
        minDefense!!.inputType = InputType.TYPE_CLASS_NUMBER
        minHP!!.inputType = InputType.TYPE_CLASS_NUMBER
        minAttackText = view.findViewById(R.id.textMinAttack)
        minDefenseText = view.findViewById(R.id.textMinDefense)
        minHPText = view.findViewById(R.id.textMinHP)

        bug = view.findViewById(R.id.bug)
        dark = view.findViewById(R.id.dark)
        dragon = view.findViewById(R.id.dragon)
        electric = view.findViewById(R.id.electric)
        fairy = view.findViewById(R.id.fairy)
        fighting = view.findViewById(R.id.fighting)
        fire = view.findViewById(R.id.fire)
        flying = view.findViewById(R.id.flying)
        ghost = view.findViewById(R.id.ghost)
        grass = view.findViewById(R.id.grass)
        ground = view.findViewById(R.id.ground)
        ice = view.findViewById(R.id.ice)
        normal = view.findViewById(R.id.normal)
        poison = view.findViewById(R.id.poison)
        psychic = view.findViewById(R.id.psychic)
        rock = view.findViewById(R.id.rock)
        steel = view.findViewById(R.id.steel)
        water = view.findViewById(R.id.water)

        return builder.create()
    }

    override fun onAttach(context: Context?) {

        super.onAttach(context)
        try {
            listener = context as FilterDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + "must implement FilterDialogListener")
        }

    }

    interface FilterDialogListener {
        fun applyTypes(switchList: HashMap<String, Boolean>, minStats: ArrayList<String>)
        fun clear()

    }
}
