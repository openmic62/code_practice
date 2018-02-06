;************************************************************************
;                                                                       *
;   Filename:      BA_L5-Flash+PB_LED-12F509.asm                  *
;   Date:          02/05/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 5, p. 6-7                                    *
;                                                                       *
;   Demonstrates use of Timer0 to maintain timing of background actions *
;   while performing other actions in response to changing inputs.      *
;                                                                       *
;   One LED simply flashes at 1 Hz (50% duty cycle).                    *
;   The other LED is only lit when the pushbutton is pressed            *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = "button pressed" indicator LED                            *
;       GP2 = flashing LED                                              *
;       GP3 = pushbutton switch (active low)                            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;	EXTERN   delay10xW_R          ; W x 10 mS delay

	;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
	UDATA
sGPIO   res 1                     ; GPIO shadow register
cnt_4ms res 1                     ; counter: increments ever 4 mS
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** SUBROUTINE VECTORS ******************************************************
;;;delay10xW                         ; W x 10 mS delay
	;;;pagesel delay10xW_R
	;;;goto    delay10xW_R
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	movlw   b'111001'         ; configure GP1 and GP1 (only) as outputs
	tris    GPIO
	; configure timer
        movlw   b'11010100'       ; configure Timer0
	        ; --0-----            timer mode (TOCS = 0)
		; ----0---            assign prescaler to Timer0 (PSA = 0)
		; -----100            set prescaler to 1:32 (PS<2:0> = 101)
	option                    ;   -> increment ever 32 uS
	
	; turn off both LEDs
	clrf    sGPIO
	clrf    GPIO
	
;***** Main loop
main_loop
	movlw   .125              ; 125 x 4 mS = 500 mS
	banksel cnt_4ms
	movwf   cnt_4ms           ; set the 4 mS counter
dly_500	; delay 500 mS seconds    
w_tmr0		
	; turn on GP1 LED if button is pressed
	; a few cycles stolen here will not risk missing the condition
	; TMR0 = 125 since TMR0 increments once every 32 uS
	bcf     sGPIO,1           ; 1 uS
	btfss   GPIO,3            ; 1 uS normally; 2 uS if true
	bsf     sGPIO,1           ; 1 uS normally not executed
	movfw   sGPIO             ; 1 uS
	movwf   GPIO              ; 1 uS
	                          ; 5 uS total every time through the loop
	
	; now do the flashing GP2 LED timing
	movfw   TMR0
	xorlw   .125              ; 125 x 32 uS = 4 mS
	btfss   STATUS,Z
	goto    w_tmr0
	clrf    TMR0              ; reset the 4 mS timer
	decfsz  cnt_4ms           ; decrement the 4 mS counter
	goto    dly_500
	
	; toggle the the GP2 LED
	movfw   sGPIO             ; get the GP2 LED state
	xorlw   b'00000100'       ; toggle the GP2 bit
	movwf   sGPIO             ; save tne new state
	movwf   GPIO              ; set the new GP2 state
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
