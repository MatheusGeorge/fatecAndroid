package com.example.pcgravacao.bossinip1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location currentLocation = null;
    private LatLng loc = null;
    private LocationManager locationManager;
    private Bitmap imageBitMap;
    private Bitmap fotoPerfil;
    private Button alterarButton;
    private Button fotoButton;
    private Button iniciarButton;
    private Button okButton;
    private TextView nomeTextView;
    private TextView paisTextView;
    private TextView tentativaTextView;
    private TextView numeroTextView;
    private TextView pontuacaoTextView;
    private TextView numeroPontuacaoTextView;
    private EditText respostaEditText;
    private ImageView perfilImageView;
    //private String[] perguntas = new String[]{"francaP", "brasilP", "inglaterraP"};
    //private String[] respostas = new String[]{"francaR", "brasilR", "inglaterraR"};
    //private double[] lat = new double[]{48.856614, -15.794157, 51.507351};
    //private double[] lng = new double[]{2.352222, -47.882529, -0.127758};
    private String[] perguntas = new String[]{"francaP", "brasilP", "inglaterraP", "coreiaSulP", "euaP", "argentinaP", "egitoP", "alemanhaP", "italiaP", "portugalP",
            "russiaP", "irlandaP", "malasiaP", "chinaP", "venezuelaP", "colombiaP", "peruP", "holandaP", "greciaP", "filipinasP", "canadaP", "equadorP", "chileP", "japaoP", "austriaP",
            "ucraniaP", "tailandiaP", "taiwanP", "emiradosP", "israelP"};


    private String[] respostas = new String[]{"francaR", "brasilR", "inglaterraR", "coreiaSulR", "euaR", "argentinaR", "egitoR", "alemanhaR", "italiaR", "portugalR",
            "russiaR", "irlandaR", "malasiaR", "chinaR", "venezuelaR", "colombiaR", "peruR", "holandaR", "greciaR", "filipinasR", "canadaR", "equadorR", "chileR", "japaoR", "austriaR",
            "ucraniaR", "tailandiaR", "taiwanR", "emiradosR", "israelR"};


    private double[] lat = new double[]{48.856614, -15.794157, 51.507351, 37.566535, 38.907192, -34.603684, 30.044420, 52.520007, 41.902783, 38.722252, 55.755826, 53.349805,
            3.139003, 39.904211, 10.480594, 4.710989, -12.046374, 52.370216, 37.983810, 14.599512, 45.421530, -0.180653, -33.448890, 35.689487, 48.208174, 50.450100,
            13.756331, 25.032969, 24.453884, 31.768319};


    private double[] lng = new double[]{2.352222, -47.882529, -0.127758, 126.977969, -77.036871, -58.381559, 31.235712, 13.404954, 12.496366, -9.139337, 55.755826, -6.260310,
            101.686855, 116.407395, -66.903606, -74.072092, -77.042793, 4.895168, 23.727539, 120.984219, -75.697193, -78.467838, -70.669265, 139.691706, 16.373819, 30.523400,
            100.501765, 121.565418, 54.377344, 35.213710};

    private List<Integer> vInicial = new ArrayList<>();
    private int indicePergunta = 0;
    private int tentativas = 2;
    private int pontuacao = 0;
    private int contResposta = 0;
    private int contG = 0;
    private static final int REQUEST_CAMERA = 2000;
    private static final int REQUEST_GPS = 1000;
    private Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(listenerOk);
        iniciarButton = (Button) findViewById(R.id.iniciarButton);
        iniciarButton.setOnClickListener(listenerIniciar);
        alterarButton = (Button) findViewById(R.id.alterarButton);
        alterarButton.setOnClickListener(listenerAlterar);
        fotoButton = (Button) findViewById(R.id.fotoButton);
        fotoButton.setOnClickListener(listenerFoto);
        pontuacaoTextView = (TextView) findViewById(R.id.pontuacaoTextView);
        numeroPontuacaoTextView = (TextView) findViewById(R.id.numeroPontuacaoTextView);
        numeroPontuacaoTextView.setText(String.valueOf(pontuacao));
        nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        paisTextView = (TextView) findViewById(R.id.paisTextView);
        tentativaTextView = (TextView) findViewById(R.id.tentativaTextView);
        numeroTextView = (TextView) findViewById(R.id.numeroTextView);
        respostaEditText = (EditText) findViewById(R.id.respostaEditText);
        perfilImageView = (ImageView) findViewById(R.id.perfilImageView);
        imageBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.bau_transparent);
        imageBitMap = Bitmap.createScaledBitmap(imageBitMap, 200, 200, true);
        okButton.setEnabled(false);
        respostaEditText.setEnabled(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                pontuacao += 2;
                numeroPontuacaoTextView.setText(String.valueOf(pontuacao));
                marker.setVisible(false);
                Toast.makeText(getBaseContext(), getString(R.string.ganhouP), Toast.LENGTH_LONG).show();
                okButton.setEnabled(true);
                respostaEditText.setEnabled(true);
                contG++;
                if (indicePergunta != 29) {
                    indicePergunta++;
                    gerarPergunta();
                }
                if (contG > 29) {
                    checkin(findViewById(R.id.map));
                    iniciarButton.setEnabled(true);
                    respostaEditText.setEnabled(false);
                    okButton.setEnabled(false);
                    tentativas = 0;
                    numeroTextView.setText("");
                    pontuacao = 0;
                    numeroPontuacaoTextView.setText("");
                    paisTextView.setText("");
                    return true;
                }
                return false;
            }

        });
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Para exibir coordenadas o app precisa do GPS", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public void checkin(View view) {
        if (mMap == null) { //somente se o mapa estiver pronto
            Toast.makeText(this, "Mapa não está pronto", Toast.LENGTH_LONG).show();
        } else if (currentLocation == null) {
            Toast.makeText(this, "Sem sinal GPS", Toast.LENGTH_LONG).show();
            loc = new LatLng(-23.5631338, -46.6543286);//vai pra Paulista
            moverLocalFinal(loc);
        } else {
            loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            moverLocalFinal(loc);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GPS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }
                }
                break;
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent();
                    }
                }
                break;
        }
    }


    private final View.OnClickListener listenerAlterar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
            final View alterarView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null);
            AlertDialog.Builder dialogName = new AlertDialog.Builder(c);
            dialogName.setView(alterarView);
            final EditText nomeDigitado = (EditText) alterarView.findViewById(R.id.userInputDialog);
            dialogName.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogBox, int id) {
                    nomeTextView.setText(nomeDigitado.getText().toString());
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogBox, int id) {
                    dialogBox.cancel();
                }
            });
            AlertDialog dialogFinalName = dialogName.create();
            dialogFinalName.show();
        }
    };

    private final View.OnClickListener listenerFoto = new View.OnClickListener() {
        @Override
        public void onClick(View V) {
            checkCameraPermission();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            fotoPerfil = (Bitmap) extras.get("data");
            perfilImageView.setImageBitmap(fotoPerfil);
            perfilImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(c, "Precisamos da sua câmera", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    private final View.OnClickListener listenerIniciar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fotoPerfil == null) {
                Toast.makeText(getBaseContext(), getString(R.string.toastAviso), Toast.LENGTH_LONG).show();
            } else {
                mMap.clear();
                contG = 0;
                okButton.setEnabled(true);
                respostaEditText.setEnabled(true);
                embaralharVetor();
                gerarPergunta();
                Toast.makeText(getBaseContext(), getString(R.string.iniciar), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void embaralharVetor() {
        for (int i = 0; i < 30; i++) {
            vInicial.add(i);
        }
        Collections.shuffle(vInicial);

    }

    private void gerarPergunta() {
        int resId = getResources().getIdentifier(perguntas[vInicial.get(indicePergunta)], "string", getPackageName());
        String randomString = getString(resId);
        paisTextView.setText(randomString);
        iniciarButton.setEnabled(false);
        numeroTextView.setText(String.valueOf(tentativas));
    }


    public void moverLocal(double lat, double lng) {
        LatLng localResposta = new LatLng(lat, lng);
        Marker bau = mMap.addMarker(new MarkerOptions().position(localResposta).title(getString(R.string.tituloBau)).icon(BitmapDescriptorFactory.fromBitmap(imageBitMap)));
        bau.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localResposta));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(localResposta).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void moverLocalFinal(LatLng loc) {
        mMap.addMarker(new MarkerOptions().position(loc).title(getString(R.string.acertos) + " " + String.valueOf(contResposta) + " " + getString(R.string.pontos) + " " + String.valueOf(pontuacao))
                .icon(BitmapDescriptorFactory.fromBitmap(fotoPerfil))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private final View.OnClickListener listenerOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int resId = getResources().getIdentifier(respostas[vInicial.get(indicePergunta)], "string", getPackageName());
            if (respostaEditText.getText().toString().toUpperCase().equals(getString(resId).toUpperCase())) {
                tentativas = 2;
                contResposta++;
                numeroTextView.setText(String.valueOf(tentativas));
                double latitude = lat[vInicial.get(indicePergunta)];
                double longitude = lng[vInicial.get(indicePergunta)];
                moverLocal(latitude, longitude);
                esconderTeclado();
                limparTeclado();

            } else if (!respostaEditText.getText().toString().toUpperCase().equals(getString(resId).toUpperCase()) && tentativas == 2) {
                tentativas = 1;
                pontuacao--;
                numeroPontuacaoTextView.setText(String.valueOf(pontuacao));
                numeroTextView.setText(String.valueOf(tentativas));
                Toast.makeText(getBaseContext(), getString(R.string.perdeuP), Toast.LENGTH_LONG).show();
            } else {
                if (indicePergunta != 29) {
                    indicePergunta++;
                    gerarPergunta();
                }
                contG++;
                tentativas = 2;
                numeroTextView.setText(String.valueOf(tentativas));
                pontuacao--;
                numeroPontuacaoTextView.setText(String.valueOf(pontuacao));
                Toast.makeText(getBaseContext(), getString(R.string.perdeuP), Toast.LENGTH_LONG).show();
            }
        }
    };

    private void esconderTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void limparTeclado() {
        respostaEditText.setText("");
        okButton.setEnabled(false);
        respostaEditText.setEnabled(false);
    }
}

